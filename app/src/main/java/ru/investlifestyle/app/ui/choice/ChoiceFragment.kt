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
import ru.investlifestyle.app.domain.CategoryAndTagsName
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
        clickSwitch()
    }

    private fun clickSwitch() {
        binding.switchHealth.setOnCheckedChangeListener { c, isChecked ->
            onCheckedListener(isChecked, CategoryAndTagsName.HEALTH.idCategory)
        }
        binding.switchKetoCourses.setOnCheckedChangeListener { c, isChecked ->
            onCheckedListener(isChecked, CategoryAndTagsName.KETOCOURSES.idCategory)
        }
        binding.switchTagsKeto.setOnCheckedChangeListener { c, isChecked ->
            onCheckedListener(isChecked, CategoryAndTagsName.TAGSKETO.idCategory)
        }
        binding.switchNutrition.setOnCheckedChangeListener { c, isChecked ->
            onCheckedListener(isChecked, CategoryAndTagsName.NUTRITION.idCategory)
        }
        binding.switchEvolution.setOnCheckedChangeListener { c, isChecked ->
            onCheckedListener(isChecked, CategoryAndTagsName.EVOLUTION.idCategory)
        }
        binding.switchTagsEducation.setOnCheckedChangeListener { c, isChecked ->
            onCheckedListener(isChecked, CategoryAndTagsName.TAGSEDUCATION.idCategory)
        }
        binding.switchTagsUseful.setOnCheckedChangeListener { c, isChecked ->
            onCheckedListener(isChecked, CategoryAndTagsName.TAGSUSEFUL.idCategory)
        }
        binding.switchTagsRecipes.setOnCheckedChangeListener { c, isChecked ->
            onCheckedListener(isChecked, CategoryAndTagsName.TAGSRECIPES.idCategory)
        }
        binding.switchTagsLikePosts.setOnCheckedChangeListener { c, isChecked ->
            onCheckedListener(isChecked, CategoryAndTagsName.LIKEPOSTS.idCategory)
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
                            binding.switchHealth.isChecked = it.find {
                                it.nameCategory == CategoryAndTagsName.HEALTH.titleCategory
                            }?.selected
                                ?: false
                            binding.switchKetoCourses.isChecked = it.find {
                                it.nameCategory == CategoryAndTagsName.KETOCOURSES.titleCategory
                            }?.selected
                                ?: false
                            binding.switchNutrition.isChecked = it.find {
                                it.nameCategory == CategoryAndTagsName.NUTRITION.titleCategory
                            }?.selected
                                ?: false
                            binding.switchEvolution.isChecked = it.find {
                                it.nameCategory == CategoryAndTagsName.EVOLUTION.titleCategory
                            }?.selected
                                ?: false
                            binding.switchTagsKeto.isChecked = it.find {
                                it.nameCategory == CategoryAndTagsName.TAGSKETO.titleCategory
                            }?.selected
                                ?: false
                            binding.switchTagsEducation.isChecked = it.find {
                                it.nameCategory == CategoryAndTagsName.TAGSEDUCATION.titleCategory
                            }?.selected
                                ?: false
                            binding.switchTagsUseful.isChecked = it.find {
                                it.nameCategory == CategoryAndTagsName.TAGSUSEFUL.titleCategory
                            }?.selected
                                ?: false
                            binding.switchTagsRecipes.isChecked = it.find {
                                it.nameCategory == CategoryAndTagsName.TAGSRECIPES.titleCategory
                            }?.selected
                                ?: false
                            binding.switchTagsLikePosts.isChecked = it.find {
                                it.nameCategory == CategoryAndTagsName.LIKEPOSTS.titleCategory
                            }?.selected
                                ?: false
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
    }
}