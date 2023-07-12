package ru.investlifestyle.app.ui.choice

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import javax.inject.Inject
import kotlinx.coroutines.launch
import ru.investlifestyle.app.App
import ru.investlifestyle.app.databinding.FragmentChoiseBinding
import ru.investlifestyle.app.ui.ViewModelFactoryTest
import ru.investlifestyle.app.ui.subject.StateListSubjects

@ExperimentalPagingApi
class ChoiceFragment : Fragment() {

    private lateinit var viewModel: ChoiceViewModel

    private val component by lazy {
        (requireActivity().application as App).daggerAppComponent
    }

    @Inject
    lateinit var viewModelFactoryTest: ViewModelFactoryTest

    private var _binding: FragmentChoiseBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChoiseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactoryTest).get(ChoiceViewModel::class.java)
        observeSubject()
        binding.switchHealth.setOnCheckedChangeListener { compoundButton, isChecked ->
            onCheckedListener(isChecked, IDHEALTH)
        }
    }

    private fun observeSubject() {
        lifecycleScope.launch {
            viewModel.allCategories.collect {
                when (it) {
                    is StateListSubjects.EmptyListSubjects -> {
                        Toast.makeText(requireContext(), "ОШИБКА БД", Toast.LENGTH_LONG).show()
                    }
                    is StateListSubjects.FilledListSubjects -> {
                        it.listSubjects.collect {
                            binding.switchHealth.isChecked = it[0].selected
                        }
                    }
                    is StateListSubjects.Error -> {
                        Toast.makeText(requireContext(), it.exception, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun onCheckedListener(isChecked: Boolean, id: Int) {
        viewModel.updateSubject(isChecked, id)
    }

    companion object {
        fun newInstance() = ChoiceFragment()

        const val IDHEALTH = 11
        const val IDKETOCOURSES = 188
        const val IDNUTRITION = 12
        const val IDEVOLUTION = 20
        const val IDTAGSKETO = 27
        const val IDTAGSEDUCATION = 22
        const val IDTAGSUSEFUL = 163
        const val IDTAGSRECIPES = 39
    }
}