package ru.investlifestyle.app.ui.subject

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject
import kotlinx.coroutines.launch
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
        binding.shimmerHealthCategories.isVisible = true
        bindingAdapter()
        observeSubjectList()

        /*subjectTopicsViewModel.loadKetoCourses.observe(viewLifecycleOwner) {
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
        }*/
    }

    private fun bindingAdapter() {
        adapterHealth = SubjectPostsAdapter(requireContext())
        adapterHealth.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
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

    private fun observeSubjectList() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                subjectTopicsViewModel.allCategories.collect {
                    when (it) {
                        is StateListSubjects.EmptyListSubjects -> {
                            binding.tvHealthCategories.visibility = ViewGroup.GONE
                            binding.rvHealthCategories.visibility = ViewGroup.GONE
                            binding.rvKetoCourses.visibility = ViewGroup.GONE
                            binding.tvKetoCourses.visibility = ViewGroup.GONE
                            binding.rvNutritionCategories.visibility = ViewGroup.GONE
                            binding.tvNutritionCategories.visibility = ViewGroup.GONE
                            binding.rvEvolution.visibility = ViewGroup.GONE
                            binding.tvEvolution.visibility = ViewGroup.GONE
                            binding.rvTagsKeto.visibility = ViewGroup.GONE
                            binding.tvTagsKeto.visibility = ViewGroup.GONE
                        }
                        is StateListSubjects.FilledListSubjects -> {

                            observeHeals()

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
                        is StateListSubjects.Error -> {
                            Toast.makeText(
                                requireContext(),
                                it.exception.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun observeHeals() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                subjectTopicsViewModel.stateLoadHealthPost.collect {
                    when (it) {
                        is StateSubjectLoaded.NotLoaded -> {
                            binding.apply {
                                tvHealthCategories.visibility = ViewGroup.GONE
                                rvHealthCategories.visibility = ViewGroup.GONE
                                shimmerHealthCategories.visibility = ViewGroup.GONE
                            }
                        }
                        is StateSubjectLoaded.Loading -> {
                            binding.apply {
                                shimmerHealthCategories.startShimmer()
                                tvHealthCategories.isVisible = false
                                rvHealthCategories.isVisible = false
                            }
                        }
                        is StateSubjectLoaded.Loaded -> {
                            binding.apply {
                                shimmerHealthCategories.isVisible = false
                                tvHealthCategories.isVisible = true
                                rvHealthCategories.isVisible = true
                                tvHealthCategories.text = HEALTH
                            }
                            adapterHealth.submitList(it.listPosts)
                        }
                        is StateSubjectLoaded.Error -> {
                            binding.apply {
                                tvHealthCategories.visibility = ViewGroup.GONE
                                rvHealthCategories.visibility = ViewGroup.GONE
                                shimmerHealthCategories.visibility = ViewGroup.GONE
                            }
                            Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
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