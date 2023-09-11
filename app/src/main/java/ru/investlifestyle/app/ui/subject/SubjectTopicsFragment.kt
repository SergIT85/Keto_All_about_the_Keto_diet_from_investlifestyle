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
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject
import ru.investlifestyle.app.App
import ru.investlifestyle.app.R
import ru.investlifestyle.app.databinding.FragmentSubjectTopicsBinding
import ru.investlifestyle.app.domain.CategoryAndTagsName
import ru.investlifestyle.app.ui.ViewModelFactoryTest
import ru.investlifestyle.app.ui.post.PostActivity
import ru.investlifestyle.app.ui.subject.adapters.FooterAdapter
import ru.investlifestyle.app.ui.subject.adapters.SubjectPostsAdapter

@ExperimentalPagingApi
class SubjectTopicsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactoryTest: ViewModelFactoryTest

    @ExperimentalPagingApi
    private val component by lazy {
        (requireActivity().application as App).daggerAppComponent
    }

    lateinit var footerAdapterHealth: FooterAdapter
    lateinit var footerAdapterEvolution: FooterAdapter
    lateinit var footerAdapterKetoCourses: FooterAdapter
    lateinit var footerAdapterNutrition: FooterAdapter
    lateinit var footerAdapterTagsKeto: FooterAdapter
    lateinit var footerAdapterTagsEducation: FooterAdapter
    lateinit var footerAdapterTagsUseful: FooterAdapter
    lateinit var footerAdapterTagsRecipes: FooterAdapter
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

    @SuppressLint("LogNotTimber")
    private fun setClickListenerTitle() {
        binding.tvKetoCourses.setOnClickListener {
            launchFragmentThemeCategory(
                CategoryAndTagsName.KETOCOURSES.idCategory,
                CategoryAndTagsName.KETOCOURSES.titleCategory,
                CategoryAndTagsName.KETOCOURSES.typeCategory
            )
        }
        binding.tvEvolution.setOnClickListener {
            launchFragmentThemeCategory(
                CategoryAndTagsName.EVOLUTION.idCategory,
                CategoryAndTagsName.EVOLUTION.titleCategory,
                CategoryAndTagsName.EVOLUTION.typeCategory
            )
        }
        binding.tvHealthCategories.setOnClickListener {
            launchFragmentThemeCategory(
                CategoryAndTagsName.HEALTH.idCategory,
                CategoryAndTagsName.HEALTH.titleCategory,
                CategoryAndTagsName.HEALTH.typeCategory
            )
        }
        binding.tvNutritionCategories.setOnClickListener {
            launchFragmentThemeCategory(
                CategoryAndTagsName.NUTRITION.idCategory,
                CategoryAndTagsName.NUTRITION.titleCategory,
                CategoryAndTagsName.NUTRITION.typeCategory
            )
        }
        binding.tvTagsEducation.setOnClickListener {
            launchFragmentThemeCategory(
                CategoryAndTagsName.TAGSEDUCATION.idCategory,
                CategoryAndTagsName.TAGSEDUCATION.titleCategory,
                CategoryAndTagsName.TAGSEDUCATION.typeCategory
            )
        }
        binding.tvTagsKeto.setOnClickListener {
            launchFragmentThemeCategory(
                CategoryAndTagsName.TAGSKETO.idCategory,
                CategoryAndTagsName.TAGSKETO.titleCategory,
                CategoryAndTagsName.TAGSKETO.typeCategory
            )
        }
        binding.tvTagsRecipes.setOnClickListener {
            launchFragmentThemeCategory(
                CategoryAndTagsName.TAGSRECIPES.idCategory,
                CategoryAndTagsName.TAGSRECIPES.titleCategory,
                CategoryAndTagsName.TAGSRECIPES.typeCategory
            )
        }
        binding.tvTagsUseful.setOnClickListener {
            launchFragmentThemeCategory(
                CategoryAndTagsName.TAGSUSEFUL.idCategory,
                CategoryAndTagsName.TAGSUSEFUL.titleCategory,
                CategoryAndTagsName.TAGSUSEFUL.typeCategory
            )
        }
        footerAdapterHealth.onPostClickListener = {
            launchFragmentThemeCategory(
                CategoryAndTagsName.HEALTH.idCategory,
                CategoryAndTagsName.HEALTH.titleCategory,
                CategoryAndTagsName.HEALTH.typeCategory
            )
        }
        footerAdapterKetoCourses.onPostClickListener = {
            launchFragmentThemeCategory(
                CategoryAndTagsName.KETOCOURSES.idCategory,
                CategoryAndTagsName.KETOCOURSES.titleCategory,
                CategoryAndTagsName.KETOCOURSES.typeCategory
            )
        }
        footerAdapterNutrition.onPostClickListener = {
            launchFragmentThemeCategory(
                CategoryAndTagsName.NUTRITION.idCategory,
                CategoryAndTagsName.NUTRITION.titleCategory,
                CategoryAndTagsName.NUTRITION.typeCategory
            )
        }
        footerAdapterEvolution.onPostClickListener = {
            launchFragmentThemeCategory(
                CategoryAndTagsName.EVOLUTION.idCategory,
                CategoryAndTagsName.EVOLUTION.titleCategory,
                CategoryAndTagsName.EVOLUTION.typeCategory
            )
        }
        footerAdapterTagsKeto.onPostClickListener = {
            launchFragmentThemeCategory(
                CategoryAndTagsName.TAGSKETO.idCategory,
                CategoryAndTagsName.TAGSKETO.titleCategory,
                CategoryAndTagsName.TAGSKETO.typeCategory
            )
        }
        footerAdapterTagsEducation.onPostClickListener = {
            launchFragmentThemeCategory(
                CategoryAndTagsName.TAGSEDUCATION.idCategory,
                CategoryAndTagsName.TAGSEDUCATION.titleCategory,
                CategoryAndTagsName.TAGSEDUCATION.typeCategory
            )
        }
        footerAdapterTagsUseful.onPostClickListener = {
            launchFragmentThemeCategory(
                CategoryAndTagsName.TAGSUSEFUL.idCategory,
                CategoryAndTagsName.TAGSUSEFUL.titleCategory,
                CategoryAndTagsName.TAGSUSEFUL.typeCategory
            )
        }
        footerAdapterTagsRecipes.onPostClickListener = {
            launchFragmentThemeCategory(
                CategoryAndTagsName.TAGSRECIPES.idCategory,
                CategoryAndTagsName.TAGSRECIPES.titleCategory,
                CategoryAndTagsName.TAGSRECIPES.typeCategory
            )
        }
    }

    private fun bindingAdapter() {

        val linearLayoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )

        adapterLikePosts = SubjectPostsAdapter(requireContext())
        binding.rvLikePosts.adapter = adapterLikePosts

        footerAdapterHealth = FooterAdapter(requireContext())
        adapterHealth = SubjectPostsAdapter(requireContext())
        binding.rvHealthCategories.run {
            /*layoutManager = linearLayoutManager*/
            adapter = ConcatAdapter(adapterHealth, footerAdapterHealth)
        }

        footerAdapterKetoCourses = FooterAdapter(requireContext())
        adapterKetoCourses = SubjectPostsAdapter((requireContext()))
        binding.rvKetoCourses.run {
            /*layoutManager = linearLayoutManager*/
            adapter = ConcatAdapter(adapterKetoCourses, footerAdapterKetoCourses)
        }

        footerAdapterNutrition = FooterAdapter(requireContext())
        adapterNutrition = SubjectPostsAdapter((requireContext()))
        binding.rvNutritionCategories.run {
            /*layoutManager = linearLayoutManager*/
            adapter = ConcatAdapter(adapterNutrition, footerAdapterNutrition)
        }

        footerAdapterEvolution = FooterAdapter(requireContext())
        adapterEvolution = SubjectPostsAdapter((requireContext()))
        binding.rvEvolution.run {
            /*layoutManager = linearLayoutManager*/
            adapter = ConcatAdapter(adapterEvolution, footerAdapterEvolution)
        }

        footerAdapterTagsKeto = FooterAdapter(requireContext())
        adapterTagsKeto = SubjectPostsAdapter((requireContext()))
        binding.rvTagsKeto.run {
            /*layoutManager = linearLayoutManager*/
            adapter = ConcatAdapter(adapterTagsKeto, footerAdapterTagsKeto)
        }

        footerAdapterTagsEducation = FooterAdapter(requireContext())
        adapterTagsEducation = SubjectPostsAdapter((requireContext()))
        binding.rvTagsEducation.run {
            /*layoutManager = linearLayoutManager*/
            adapter = ConcatAdapter(adapterTagsEducation, footerAdapterTagsEducation)
        }

        footerAdapterTagsUseful = FooterAdapter(requireContext())
        adapterTagsUseful = SubjectPostsAdapter((requireContext()))
        binding.rvTagsUseful.run {
            /*layoutManager = linearLayoutManager*/
            adapter = ConcatAdapter(adapterTagsUseful, footerAdapterTagsUseful)
        }

        footerAdapterTagsRecipes = FooterAdapter(requireContext())
        adapterTagsRecipes = SubjectPostsAdapter((requireContext()))
        binding.rvTagsRecipes.run {
            /*layoutManager = linearLayoutManager*/
            adapter = ConcatAdapter(adapterTagsRecipes, footerAdapterTagsRecipes)
        }
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
                                    it.nameCategory == CategoryAndTagsName.LIKEPOSTS.titleCategory
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
                                                binding.tvLikePosts.text =
                                                    CategoryAndTagsName.LIKEPOSTS.titleCategory
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
                                    it.nameCategory == CategoryAndTagsName.HEALTH.titleCategory
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
                                                binding.tvHealthCategories.text =
                                                    CategoryAndTagsName.HEALTH.titleCategory
                                                binding.shimmerHealthCategories.isVisible = false
                                            }
                                        subjectTopicsViewModel.footerAdapterLiveDataHealth.observe(
                                            viewLifecycleOwner
                                        ) {
                                            footerAdapterHealth.submitList(listOf(it))
                                        }
                                    }
                                    subjectTopicsViewModel.loadPostsHealth(
                                        it.find {
                                            it.nameCategory ==
                                                CategoryAndTagsName.HEALTH.titleCategory
                                        }!!.idCategory
                                    )
                                } else {
                                    binding.apply {
                                        tvHealthCategories.visibility = ViewGroup.GONE
                                        rvHealthCategories.visibility = ViewGroup.GONE
                                        shimmerHealthCategories.visibility = ViewGroup.GONE
                                    }
                                }

                                if (it.find {
                                    it.nameCategory == CategoryAndTagsName
                                        .KETOCOURSES
                                        .titleCategory
                                }?.selected == true
                                ) {
                                    if (adapterKetoCourses.itemCount == 0) {
                                        binding.shimmerKetoCourses.isVisible = true
                                        subjectTopicsViewModel
                                            .loadKetoCourses
                                            .observe(viewLifecycleOwner) {
                                                binding.tvKetoCourses.isVisible = true
                                                binding.rvKetoCourses.isVisible = true
                                                adapterKetoCourses.submitList(it)
                                                binding.tvKetoCourses.text = CategoryAndTagsName
                                                    .KETOCOURSES
                                                    .titleCategory
                                                binding.shimmerKetoCourses.isVisible = false
                                            }
                                    }
                                    subjectTopicsViewModel.footerAdapterLiveDataKetoCourses.observe(
                                        viewLifecycleOwner
                                    ) {
                                        footerAdapterKetoCourses.submitList(listOf(it))
                                    }
                                    subjectTopicsViewModel.loadPostsKetoCourses(
                                        it.find {
                                            it.nameCategory == CategoryAndTagsName
                                                .KETOCOURSES
                                                .titleCategory
                                        }!!.idCategory
                                    )
                                } else {
                                    binding.apply {
                                        tvKetoCourses.visibility = ViewGroup.GONE
                                        rvKetoCourses.visibility = ViewGroup.GONE
                                        shimmerKetoCourses.visibility = ViewGroup.GONE
                                    }
                                }

                                if (it.find {
                                    it.nameCategory == CategoryAndTagsName
                                        .NUTRITION
                                        .titleCategory
                                }?.selected == true
                                ) {
                                    if (adapterNutrition.itemCount == 0) {
                                        binding.shimmerNutritionCategories.isVisible = true
                                        subjectTopicsViewModel
                                            .loadNutrition
                                            .observe((viewLifecycleOwner)) {
                                                binding.tvNutritionCategories.isVisible = true
                                                binding.rvNutritionCategories.isVisible = true
                                                adapterNutrition.submitList(it)
                                                binding.tvNutritionCategories.text =
                                                    CategoryAndTagsName
                                                        .NUTRITION
                                                        .titleCategory
                                                binding.shimmerNutritionCategories
                                                    .isVisible = false
                                            }
                                    }
                                    subjectTopicsViewModel.footerAdapterLiveDataNutrition.observe(
                                        viewLifecycleOwner
                                    ) {
                                        footerAdapterNutrition.submitList(listOf(it))
                                    }
                                    subjectTopicsViewModel.loadPostsNutrition(
                                        it.find {
                                            it.nameCategory == CategoryAndTagsName
                                                .NUTRITION
                                                .titleCategory
                                        }!!.idCategory
                                    )
                                } else {
                                    binding.apply {
                                        shimmerNutritionCategories.visibility = ViewGroup.GONE
                                        tvNutritionCategories.visibility = ViewGroup.GONE
                                        rvNutritionCategories.visibility = ViewGroup.GONE
                                    }
                                }

                                if (it.find {
                                    it.nameCategory == CategoryAndTagsName
                                        .EVOLUTION
                                        .titleCategory
                                }?.selected == true
                                ) {
                                    if (adapterEvolution.itemCount == 0) {
                                        binding.shimmerEvolution.isVisible = true
                                        subjectTopicsViewModel
                                            .loadEvolution
                                            .observe(viewLifecycleOwner) {
                                                binding.tvEvolution.isVisible = true
                                                binding.rvEvolution.isVisible = true
                                                adapterEvolution.submitList(it)
                                                binding.tvEvolution.text = CategoryAndTagsName
                                                    .EVOLUTION
                                                    .titleCategory
                                                binding.shimmerEvolution.isVisible = false
                                            }
                                    }
                                    subjectTopicsViewModel.footerAdapterLiveDataEvolution.observe(
                                        viewLifecycleOwner
                                    ) {
                                        footerAdapterEvolution.submitList(listOf(it))
                                    }
                                    subjectTopicsViewModel.loadPostsEvolution(
                                        it.find {
                                            it.nameCategory == CategoryAndTagsName
                                                .EVOLUTION
                                                .titleCategory
                                        }!!.idCategory
                                    )
                                } else {
                                    binding.apply {
                                        shimmerEvolution.visibility = ViewGroup.GONE
                                        tvEvolution.visibility = ViewGroup.GONE
                                        rvEvolution.visibility = ViewGroup.GONE
                                    }
                                }

                                if (it.find {
                                    it.nameCategory == CategoryAndTagsName
                                        .TAGSKETO
                                        .titleCategory
                                }?.selected == true
                                ) {
                                    if (adapterTagsKeto.itemCount == 0) {
                                        binding.shimmerTagsKeto.isVisible = true
                                        subjectTopicsViewModel
                                            .loadTagsKeto
                                            .observe(viewLifecycleOwner) {
                                                binding.tvTagsKeto.isVisible = true
                                                binding.rvTagsKeto.isVisible = true
                                                adapterTagsKeto.submitList(it)
                                                binding.tvTagsKeto.text = CategoryAndTagsName
                                                    .TAGSKETO
                                                    .titleCategory
                                                binding.shimmerTagsKeto.isVisible = false
                                            }
                                    }
                                    subjectTopicsViewModel.footerAdapterLiveDataTagsKeto.observe(
                                        viewLifecycleOwner
                                    ) {
                                        footerAdapterTagsKeto.submitList(listOf(it))
                                    }
                                    subjectTopicsViewModel.loadPostsTagsKeto(
                                        it.find {
                                            it.nameCategory == CategoryAndTagsName
                                                .TAGSKETO
                                                .titleCategory
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
                                    it.nameCategory == CategoryAndTagsName
                                        .TAGSEDUCATION
                                        .titleCategory
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
                                                binding.tvTagsEducation.text = CategoryAndTagsName
                                                    .TAGSEDUCATION
                                                    .titleCategory
                                                binding.shimmerTagsEducation.isVisible = false
                                            }
                                    }
                                    subjectTopicsViewModel
                                        .footerAdapterLiveDataTagsEducation
                                        .observe(viewLifecycleOwner) {
                                            footerAdapterTagsEducation.submitList(listOf(it))
                                        }
                                    subjectTopicsViewModel.loadPostsTagsEducation(
                                        it.find {
                                            it.nameCategory == CategoryAndTagsName
                                                .TAGSEDUCATION
                                                .titleCategory
                                        }!!.idCategory
                                    )
                                } else {
                                    binding.apply {
                                        shimmerTagsEducation.visibility = ViewGroup.GONE
                                        tvTagsEducation.visibility = ViewGroup.GONE
                                        rvTagsEducation.visibility = ViewGroup.GONE
                                    }
                                }

                                if (it.find {
                                    it.nameCategory == CategoryAndTagsName
                                        .TAGSUSEFUL
                                        .titleCategory
                                }?.selected == true
                                ) {
                                    if (adapterTagsUseful.itemCount == 0) {
                                        binding.shimmerTagsUseful.isVisible = true
                                        subjectTopicsViewModel
                                            .loadTagsUseful
                                            .observe(viewLifecycleOwner) {
                                                binding.tvTagsUseful.isVisible = true
                                                binding.rvTagsUseful.isVisible = true
                                                adapterTagsUseful.submitList(it)
                                                binding.tvTagsUseful.text = CategoryAndTagsName
                                                    .TAGSUSEFUL
                                                    .titleCategory
                                                binding.shimmerTagsUseful.isVisible = false
                                            }
                                    }
                                    subjectTopicsViewModel.footerAdapterLiveDataTagsUseful.observe(
                                        viewLifecycleOwner
                                    ) {
                                        footerAdapterTagsUseful.submitList(listOf(it))
                                    }
                                    subjectTopicsViewModel.loadPostsTagsUseful(
                                        it.find {
                                            it.nameCategory == CategoryAndTagsName
                                                .TAGSUSEFUL
                                                .titleCategory
                                        }!!.idCategory
                                    )
                                } else {
                                    binding.apply {
                                        shimmerTagsUseful.visibility = ViewGroup.GONE
                                        tvTagsUseful.visibility = ViewGroup.GONE
                                        rvTagsUseful.visibility = ViewGroup.GONE
                                    }
                                }

                                if (it.find {
                                    it.nameCategory == CategoryAndTagsName
                                        .TAGSRECIPES
                                        .titleCategory
                                }?.selected == true
                                ) {
                                    if (adapterTagsRecipes.itemCount == 0) {
                                        binding.shimmerTagsRecipes.isVisible = true
                                        subjectTopicsViewModel
                                            .loadTagsRecipes
                                            .observe(viewLifecycleOwner) {
                                                binding.tvTagsRecipes.isVisible = true
                                                binding.rvTagsRecipes.isVisible = true
                                                adapterTagsRecipes.submitList(it)
                                                binding.tvTagsRecipes.text = CategoryAndTagsName
                                                    .TAGSRECIPES
                                                    .titleCategory
                                                binding.shimmerTagsRecipes.isVisible = false
                                            }
                                    }
                                    subjectTopicsViewModel
                                        .footerAdapterLiveDataTagsRecipes
                                        .observe(viewLifecycleOwner) {
                                            footerAdapterTagsRecipes.submitList(listOf(it))
                                        }
                                    subjectTopicsViewModel.loadPostsTagsRecipes(
                                        it.find {
                                            it.nameCategory == CategoryAndTagsName
                                                .TAGSRECIPES
                                                .titleCategory
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
}