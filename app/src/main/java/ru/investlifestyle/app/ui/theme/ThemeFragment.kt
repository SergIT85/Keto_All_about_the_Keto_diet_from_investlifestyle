package ru.investlifestyle.app.ui.theme

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import javax.inject.Inject
import ru.investlifestyle.app.App
import ru.investlifestyle.app.databinding.FragmentThemeBinding
import ru.investlifestyle.app.ui.ViewModelFactoryTest
import ru.investlifestyle.app.ui.theme.adapters.ThemePostsAdapter

@ExperimentalPagingApi
class ThemeFragment : Fragment() {

    private val args by navArgs<ThemeFragmentArgs>()
    private lateinit var adapterSubject: ThemePostsAdapter

    private var _binding: FragmentThemeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: ThemeViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactoryTest

    private val component by lazy {
        (requireActivity().application as App).daggerAppComponent
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThemeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ThemeViewModel::class.java)
        bindingAdapter()
        observeAdapter()
        binding.themeDetailShimmerLayout.isVisible = false
        binding.themeCoordinator.isVisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindingAdapter() {
        adapterSubject = ThemePostsAdapter(requireContext())
        binding.rvSubject.adapter = adapterSubject
    }

    private fun observeAdapter() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getPostLivedataByCategoryId(args.categoryId)
            viewModel.postLiveData.observe(viewLifecycleOwner) {
                adapterSubject.submitList(it)
            }
        }
    }

    companion object {

        private const val LIKEPOSTS = "Сохранённые"
        private const val HEALTH = "Здоровье"
        private const val KETOCOURSES = "KETOCOURSES"
        private const val NUTRITION = "Питание"
        private const val EVOLUTION = "Развитие"
        private const val TAGSKETO = "Кето"
        private const val TAGSEDUCATION = "Обучение"
        private const val TAGSUSEFUL = "Полезное"
        private const val TAGSRECIPES = "Рецепты"

        private const val IDLIKEPOSTS = 0
        private const val IDHEALTH = 11
        private const val IDKETOCOURSES = 188
        private const val IDNUTRITION = 12
        private const val IDEVOLUTION = 20
        private const val IDTAGSKETO = 27
        private const val IDTAGSEDUCATION = 22
        private const val IDTAGSUSEFUL = 163
        private const val IDTAGSRECIPES = 39

        private const val CATEGORIESID = "categories"
        private const val TAGS = "tags"
        fun newInstanceThemeFragmentCategories(categoryId: Int): ThemeFragment {
            return ThemeFragment().apply {
                arguments = Bundle().apply {
                    putInt(CATEGORIESID, categoryId)
                }
            }
        }
    }
}