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
        clickSwitch()
    }

    private fun clickSwitch() {
        binding.switchHealth.setOnCheckedChangeListener { c, isChecked ->
            onCheckedListener(isChecked, IDHEALTH)
        }
        binding.switchKetoCourses.setOnCheckedChangeListener { c, isChecked ->
            onCheckedListener(isChecked, IDKETOCOURSES)
        }
        binding.switchTagsKeto.setOnCheckedChangeListener { c, isChecked ->
            onCheckedListener(isChecked, IDTAGSKETO)
        }
        binding.switchNutrition.setOnCheckedChangeListener { c, isChecked ->
            onCheckedListener(isChecked, IDNUTRITION)
        }
        binding.switchEvolution.setOnCheckedChangeListener { c, isChecked ->
            onCheckedListener(isChecked, IDEVOLUTION)
        }
        binding.switchTagsEducation.setOnCheckedChangeListener { c, isChecked ->
            onCheckedListener(isChecked, IDTAGSEDUCATION)
        }
        binding.switchTagsUseful.setOnCheckedChangeListener { c, isChecked ->
            onCheckedListener(isChecked, IDTAGSUSEFUL)
        }
        binding.switchTagsRecipes.setOnCheckedChangeListener { c, isChecked ->
            onCheckedListener(isChecked, IDTAGSRECIPES)
        }
        binding.switchTagsLikePosts.setOnCheckedChangeListener { c, isChecked ->
            onCheckedListener(isChecked, IDLIKEPOSTS)
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
                                it.nameCategory == HEALTH
                            }?.selected
                                ?: false
                            binding.switchKetoCourses.isChecked = it.find {
                                it.nameCategory == KETOCOURSES
                            }?.selected
                                ?: false
                            binding.switchNutrition.isChecked = it.find {
                                it.nameCategory == NUTRITION
                            }?.selected
                                ?: false
                            binding.switchEvolution.isChecked = it.find {
                                it.nameCategory == EVOLUTION
                            }?.selected
                                ?: false
                            binding.switchTagsKeto.isChecked = it.find {
                                it.nameCategory == TAGSKETO
                            }?.selected
                                ?: false
                            binding.switchTagsEducation.isChecked = it.find {
                                it.nameCategory == TAGSEDUCATION
                            }?.selected
                                ?: false
                            binding.switchTagsUseful.isChecked = it.find {
                                it.nameCategory == TAGSUSEFUL
                            }?.selected
                                ?: false
                            binding.switchTagsRecipes.isChecked = it.find {
                                it.nameCategory == TAGSRECIPES
                            }?.selected
                                ?: false
                            binding.switchTagsLikePosts.isChecked = it.find {
                                it.nameCategory == LIKEPOSTS
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

        const val LIKEPOSTS = "Сохранённые"
        const val IDHEALTH = 11
        const val IDKETOCOURSES = 188
        const val IDNUTRITION = 12
        const val IDEVOLUTION = 20
        const val IDTAGSKETO = 27
        const val IDTAGSEDUCATION = 22
        const val IDTAGSUSEFUL = 163
        const val IDTAGSRECIPES = 39

        const val IDLIKEPOSTS = 0
        const val HEALTH = "Здоровье"
        const val KETOCOURSES = "Кето курс"
        const val NUTRITION = "Питание"
        const val EVOLUTION = "Развитие"
        const val TAGSKETO = "Кето"
        const val TAGSEDUCATION = "Обучение"
        const val TAGSUSEFUL = "Полезное"
        const val TAGSRECIPES = "Рецепты"
    }
}