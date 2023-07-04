package ru.investlifestyle.app.ui.subject

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import javax.inject.Inject
import ru.investlifestyle.app.App
import ru.investlifestyle.app.databinding.FragmentSubjectTopicsBinding
import ru.investlifestyle.app.ui.ViewModelFactoryTest
import ru.investlifestyle.app.ui.subject.adapters.SubjectPostsAdapter

class SubjectTopicsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactoryTest: ViewModelFactoryTest

    @ExperimentalPagingApi
    private val component by lazy {
        (requireActivity().application as App).daggerAppComponent
    }

    lateinit var adapterHealth: SubjectPostsAdapter
    lateinit var adapterKetoCourses: SubjectPostsAdapter
    lateinit var adapterNutrition: SubjectPostsAdapter
    lateinit var adapterEvolution: SubjectPostsAdapter
    lateinit var adapterTagsKeto: SubjectPostsAdapter

    lateinit var subjectTopicsViewModel: SubjectTopicsViewModel

    private var _binding: FragmentSubjectTopicsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @ExperimentalPagingApi
    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        subjectTopicsViewModel =
            ViewModelProvider(this, viewModelFactoryTest).get(SubjectTopicsViewModel::class.java)
        _binding = FragmentSubjectTopicsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("CheckResult", "FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingAdapter()
        subjectTopicsViewModel.loadHealthPost.observe(viewLifecycleOwner) {
            adapterHealth.submitList(it)
            binding.tvHealthCategories.text = HEALTH
        }

        subjectTopicsViewModel.loadKetoCourses.observe(viewLifecycleOwner) {
            adapterKetoCourses.submitList(it)
            binding.tvKetoCourses.text = KETOCOURSES
        }

        subjectTopicsViewModel.loadNutrition.observe(viewLifecycleOwner) {
            adapterNutrition.submitList(it)
            binding.tvNutritionCategories.text = NUTRITION
        }

        subjectTopicsViewModel.loadEvolution.observe(viewLifecycleOwner) {
            adapterEvolution.submitList(it)
            binding.tvEvolution.text = EVOLUTION
        }

        subjectTopicsViewModel.loadSubjectTagsPost.observe(viewLifecycleOwner) {
            adapterTagsKeto.submitList(it)
            binding.tvTagsKeto.text = TAGSKETO
        }
    }
    private fun bindingAdapter() {
        adapterHealth = SubjectPostsAdapter(requireContext())
        binding.rvHealthCategories.adapter = adapterHealth

        adapterKetoCourses = SubjectPostsAdapter((requireContext()))
        binding.rvKetoCourses.adapter = adapterKetoCourses

        adapterNutrition = SubjectPostsAdapter((requireContext()))
        binding.rvNutritionCategories.adapter = adapterNutrition

        adapterEvolution = SubjectPostsAdapter((requireContext()))
        binding.rvEvolution.adapter = adapterEvolution

        adapterTagsKeto = SubjectPostsAdapter((requireContext()))
        binding.rvTagsKeto.adapter = adapterTagsKeto
    }

    companion object {
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