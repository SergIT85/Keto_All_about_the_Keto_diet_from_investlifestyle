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
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import ru.investlifestyle.app.App
import ru.investlifestyle.app.R
import ru.investlifestyle.app.databinding.FragmentSubjectTopicsBinding
import ru.investlifestyle.app.ui.ViewModelFactoryTest
import ru.investlifestyle.app.ui.post.PostActivity
import ru.investlifestyle.app.ui.subject.adapters.SubjectPostsAdapter

@ExperimentalPagingApi
class SubjectTopicsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactoryTest: ViewModelFactoryTest

    @ExperimentalPagingApi
    private val component by lazy {
        (requireActivity().application as App).daggerAppComponent
    }

    lateinit var adapterLikePosts: SubjectPostsAdapter
    lateinit var adapterHealth: SubjectPostsAdapter
    lateinit var adapterKetoCourses: SubjectPostsAdapter
    lateinit var adapterNutrition: SubjectPostsAdapter
    lateinit var adapterEvolution: SubjectPostsAdapter
    lateinit var adapterTagsKeto: SubjectPostsAdapter
    lateinit var adapterTagsEducation: SubjectPostsAdapter
    lateinit var adapterTagsUseful: SubjectPostsAdapter
    lateinit var adapterTagsRecipes: SubjectPostsAdapter

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
        observeSubjectList()
        setChoiceSubjectClickListener()
        setClickListener()
        setClickListenerTitle()
    }

    private fun setClickListener() {
        adapterHealth.onPostClickListener = {
            val intent = PostActivity.intentPostActivity(requireContext(), it.id)
            startActivity(intent)
        }
        adapterEvolution.onPostClickListener = {
            val intent = PostActivity.intentPostActivity(requireContext(), it.id)
            startActivity(intent)
        }
        adapterTagsKeto.onPostClickListener = {
            val intent = PostActivity.intentPostActivity(requireContext(), it.id)
            startActivity(intent)
        }
        adapterNutrition.onPostClickListener = {
            val intent = PostActivity.intentPostActivity(requireContext(), it.id)
            startActivity(intent)
        }
        adapterKetoCourses.onPostClickListener = {
            val intent = PostActivity.intentPostActivity(requireContext(), it.id)
            startActivity(intent)
        }
        adapterTagsEducation.onPostClickListener = {
            val intent = PostActivity.intentPostActivity(requireContext(), it.id)
            startActivity(intent)
        }
        adapterTagsRecipes.onPostClickListener = {
            val intent = PostActivity.intentPostActivity(requireContext(), it.id)
            startActivity(intent)
        }
        adapterTagsUseful.onPostClickListener = {
            val intent = PostActivity.intentPostActivity(requireContext(), it.id)
            startActivity(intent)
        }
        adapterLikePosts.onPostClickListener = {
            val intent = PostActivity.intentPostActivity(requireContext(), it.id)
            startActivity(intent)
        }
    }

    private fun setChoiceSubjectClickListener() {
        binding.correctSubjectFooter.setOnClickListener {
            launchFragment()
        }
    }

    private fun launchFragment() {
        val navHostFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main)
        navHostFragment?.findNavController()?.navigate(R.id.choiceFragment)
    }

    private fun launchFragmentThemeCategory(
        idCategory: Int,
        titleCategory: String,
        typeCategory: String
    ) {
        findNavController().navigate(
            SubjectTopicsFragmentDirections.actionNavigationDashboardToThemeFragment(
                idCategory,
                titleCategory,
                typeCategory
            )
        )
    }

    private fun setClickListenerTitle() {
        binding.tvKetoCourses.setOnClickListener {
            launchFragmentThemeCategory(
                IDKETOCOURSES,
                KETOCOURSES,
                CATEGORIES
            )
        }
        binding.tvEvolution.setOnClickListener {
            launchFragmentThemeCategory(
                IDEVOLUTION,
                EVOLUTION,
                CATEGORIES
            )
        }
        binding.tvHealthCategories.setOnClickListener {
            launchFragmentThemeCategory(
                IDHEALTH,
                HEALTH,
                CATEGORIES
            )
        }
        binding.tvNutritionCategories.setOnClickListener {
            launchFragmentThemeCategory(
                IDNUTRITION,
                NUTRITION,
                CATEGORIES
            )
        }
    }

    private fun bindingAdapter() {

        adapterLikePosts = SubjectPostsAdapter(requireContext())
        binding.rvLikePosts.adapter = adapterLikePosts

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

        adapterTagsEducation = SubjectPostsAdapter((requireContext()))
        binding.rvTagsEducation.adapter = adapterTagsEducation

        adapterTagsUseful = SubjectPostsAdapter((requireContext()))
        binding.rvTagsUseful.adapter = adapterTagsUseful

        adapterTagsRecipes = SubjectPostsAdapter((requireContext()))
        binding.rvTagsRecipes.adapter = adapterTagsRecipes
    }

    private fun observeSubjectList() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                subjectTopicsViewModel.allCategories.collect {
                    when (it) {
                        is StateListSubjects.EmptyListSubjects -> {
                        }

                        is StateListSubjects.FilledListSubjects -> {
                            it.listSubjects.collect {
                                if (it.find {
                                    it.nameCategory == LIKEPOSTS
                                }?.selected == true
                                ) {
                                    if (adapterLikePosts.itemCount == 0) {
                                        binding.shimmerLikePosts.isVisible = true
                                        subjectTopicsViewModel
                                            .loadLikePosts
                                            .observe(viewLifecycleOwner) {
                                                binding.tvLikePosts.isVisible = true
                                                binding.rvLikePosts.isVisible = true
                                                adapterLikePosts.submitList(it)
                                                binding.tvLikePosts.text = LIKEPOSTS
                                                binding.shimmerLikePosts.isVisible = false
                                            }
                                    }
                                    subjectTopicsViewModel.loadLikePost()
                                } else {
                                    binding.apply {
                                        tvLikePosts.visibility = ViewGroup.GONE
                                        rvLikePosts.visibility = ViewGroup.GONE
                                        shimmerLikePosts.visibility = ViewGroup.GONE
                                    }
                                }

                                if (it.find {
                                    it.nameCategory == HEALTH
                                }?.selected == true
                                ) {
                                    if (adapterHealth.itemCount == 0) {
                                        binding.shimmerHealthCategories.isVisible = true
                                        subjectTopicsViewModel
                                            .loadHealth
                                            .observe(viewLifecycleOwner) {
                                                binding.tvHealthCategories.isVisible = true
                                                binding.rvHealthCategories.isVisible = true
                                                adapterHealth.submitList(it)
                                                binding.tvHealthCategories.text = HEALTH
                                                binding.shimmerHealthCategories.isVisible = false
                                            }
                                    }

                                    subjectTopicsViewModel.loadPostsHealth(
                                        it.find {
                                            it.nameCategory == HEALTH
                                        }!!.idCategory
                                    )
                                } else {
                                    binding.apply {
                                        tvHealthCategories.visibility = ViewGroup.GONE
                                        rvHealthCategories.visibility = ViewGroup.GONE
                                        shimmerHealthCategories.visibility = ViewGroup.GONE
                                    }
                                }

                                if (it.find { it.nameCategory == KETOCOURSES }?.selected == true) {
                                    if (adapterKetoCourses.itemCount == 0) {
                                        binding.shimmerKetoCourses.isVisible = true
                                        subjectTopicsViewModel
                                            .loadKetoCourses
                                            .observe(viewLifecycleOwner) {
                                                binding.tvKetoCourses.isVisible = true
                                                binding.rvKetoCourses.isVisible = true
                                                adapterKetoCourses.submitList(it)
                                                binding.tvKetoCourses.text = KETOCOURSES
                                                binding.shimmerKetoCourses.isVisible = false
                                            }
                                    }
                                    subjectTopicsViewModel.loadPostsKetoCourses(
                                        it.find {
                                            it.nameCategory == KETOCOURSES
                                        }!!.idCategory
                                    )
                                } else {
                                    binding.apply {
                                        tvKetoCourses.visibility = ViewGroup.GONE
                                        rvKetoCourses.visibility = ViewGroup.GONE
                                        shimmerKetoCourses.visibility = ViewGroup.GONE
                                    }
                                }

                                if (it.find { it.nameCategory == NUTRITION }?.selected == true) {
                                    if (adapterNutrition.itemCount == 0) {
                                        binding.shimmerNutritionCategories.isVisible = true
                                        subjectTopicsViewModel
                                            .loadNutrition
                                            .observe((viewLifecycleOwner)) {
                                                binding.tvNutritionCategories.isVisible = true
                                                binding.rvNutritionCategories.isVisible = true
                                                adapterNutrition.submitList(it)
                                                binding.tvNutritionCategories.text = NUTRITION
                                                binding.shimmerNutritionCategories.isVisible = false
                                            }
                                    }
                                    subjectTopicsViewModel.loadPostsNutrition(
                                        it.find {
                                            it.nameCategory == NUTRITION
                                        }!!.idCategory
                                    )
                                } else {
                                    binding.apply {
                                        shimmerNutritionCategories.visibility = ViewGroup.GONE
                                        tvNutritionCategories.visibility = ViewGroup.GONE
                                        rvNutritionCategories.visibility = ViewGroup.GONE
                                    }
                                }

                                if (it.find { it.nameCategory == EVOLUTION }?.selected == true) {
                                    if (adapterEvolution.itemCount == 0) {
                                        binding.shimmerEvolution.isVisible = true
                                        subjectTopicsViewModel
                                            .loadEvolution
                                            .observe(viewLifecycleOwner) {
                                                binding.tvEvolution.isVisible = true
                                                binding.rvEvolution.isVisible = true
                                                adapterEvolution.submitList(it)
                                                binding.tvEvolution.text = EVOLUTION
                                                binding.shimmerEvolution.isVisible = false
                                            }
                                    }
                                    subjectTopicsViewModel.loadPostsEvolution(
                                        it.find {
                                            it.nameCategory == EVOLUTION
                                        }!!.idCategory
                                    )
                                } else {
                                    binding.apply {
                                        shimmerEvolution.visibility = ViewGroup.GONE
                                        tvEvolution.visibility = ViewGroup.GONE
                                        rvEvolution.visibility = ViewGroup.GONE
                                    }
                                }

                                if (it.find { it.nameCategory == TAGSKETO }?.selected == true) {
                                    if (adapterTagsKeto.itemCount == 0) {
                                        binding.shimmerTagsKeto.isVisible = true
                                        subjectTopicsViewModel
                                            .loadTagsKeto
                                            .observe(viewLifecycleOwner) {
                                                binding.tvTagsKeto.isVisible = true
                                                binding.rvTagsKeto.isVisible = true
                                                adapterTagsKeto.submitList(it)
                                                binding.tvTagsKeto.text = TAGSKETO
                                                binding.shimmerTagsKeto.isVisible = false
                                            }
                                    }
                                    subjectTopicsViewModel.loadPostsTagsKeto(
                                        it.find {
                                            it.nameCategory == TAGSKETO
                                        }!!.idCategory
                                    )
                                } else {
                                    binding.apply {
                                        shimmerTagsKeto.visibility = ViewGroup.GONE
                                        tvTagsKeto.visibility = ViewGroup.GONE
                                        rvTagsKeto.visibility = ViewGroup.GONE
                                    }
                                }

                                if (it.find {
                                    it.nameCategory == TAGSEDUCATION
                                }?.selected == true
                                ) {
                                    if (adapterTagsEducation.itemCount == 0) {
                                        binding.shimmerTagsEducation.isVisible = true
                                        subjectTopicsViewModel
                                            .loadTagsEducation
                                            .observe(viewLifecycleOwner) {
                                                binding.tvTagsEducation.isVisible = true
                                                binding.rvTagsEducation.isVisible = true
                                                adapterTagsEducation.submitList(it)
                                                binding.tvTagsEducation.text = TAGSEDUCATION
                                                binding.shimmerTagsEducation.isVisible = false
                                            }
                                    }
                                    subjectTopicsViewModel.loadPostsTagsEducation(
                                        it.find {
                                            it.nameCategory == TAGSEDUCATION
                                        }!!.idCategory
                                    )
                                } else {
                                    binding.apply {
                                        shimmerTagsEducation.visibility = ViewGroup.GONE
                                        tvTagsEducation.visibility = ViewGroup.GONE
                                        rvTagsEducation.visibility = ViewGroup.GONE
                                    }
                                }

                                if (it.find { it.nameCategory == TAGSUSEFUL }?.selected == true) {
                                    if (adapterTagsUseful.itemCount == 0) {
                                        binding.shimmerTagsUseful.isVisible = true
                                        subjectTopicsViewModel
                                            .loadTagsUseful
                                            .observe(viewLifecycleOwner) {
                                                binding.tvTagsUseful.isVisible = true
                                                binding.rvTagsUseful.isVisible = true
                                                adapterTagsUseful.submitList(it)
                                                binding.tvTagsUseful.text = TAGSUSEFUL
                                                binding.shimmerTagsUseful.isVisible = false
                                            }
                                    }
                                    subjectTopicsViewModel.loadPostsTagsUseful(
                                        it.find {
                                            it.nameCategory == TAGSUSEFUL
                                        }!!.idCategory
                                    )
                                } else {
                                    binding.apply {
                                        shimmerTagsUseful.visibility = ViewGroup.GONE
                                        tvTagsUseful.visibility = ViewGroup.GONE
                                        rvTagsUseful.visibility = ViewGroup.GONE
                                    }
                                }

                                if (it.find { it.nameCategory == TAGSRECIPES }?.selected == true) {
                                    if (adapterTagsRecipes.itemCount == 0) {
                                        binding.shimmerTagsRecipes.isVisible = true
                                        subjectTopicsViewModel
                                            .loadTagsRecipes
                                            .observe(viewLifecycleOwner) {
                                                binding.tvTagsRecipes.isVisible = true
                                                binding.rvTagsRecipes.isVisible = true
                                                adapterTagsRecipes.submitList(it)
                                                binding.tvTagsRecipes.text = TAGSRECIPES
                                                binding.shimmerTagsRecipes.isVisible = false
                                            }
                                    }
                                    subjectTopicsViewModel.loadPostsTagsRecipes(
                                        it.find {
                                            it.nameCategory == TAGSRECIPES
                                        }!!.idCategory
                                    )
                                } else {
                                    binding.apply {
                                        shimmerTagsRecipes.visibility = ViewGroup.GONE
                                        tvTagsRecipes.visibility = ViewGroup.GONE
                                        rvTagsRecipes.visibility = ViewGroup.GONE
                                    }
                                }
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

    companion object {
        const val LIKEPOSTS = "Сохранённые"
        const val HEALTH = "Здоровье"
        const val KETOCOURSES = "Кето курс"
        const val NUTRITION = "Питание"
        const val EVOLUTION = "Развитие"
        const val TAGSKETO = "Кето"
        const val TAGSEDUCATION = "Обучение"
        const val TAGSUSEFUL = "Полезное"
        const val TAGSRECIPES = "Рецепты"

        private const val IDLIKEPOSTS = 0
        private const val IDHEALTH = 11
        private const val IDKETOCOURSES = 188
        private const val IDNUTRITION = 12
        private const val IDEVOLUTION = 20
        private const val IDTAGSKETO = 27
        private const val IDTAGSEDUCATION = 22
        private const val IDTAGSUSEFUL = 163
        private const val IDTAGSRECIPES = 39

        const val CATEGORIES = "categories"
        const val TAGS = "tags"
    }
}