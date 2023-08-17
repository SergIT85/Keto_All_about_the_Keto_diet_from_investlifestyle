package ru.investlifestyle.app.ui.theme

import android.annotation.SuppressLint
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
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.investlifestyle.app.App
import ru.investlifestyle.app.databinding.FragmentThemeBinding
import ru.investlifestyle.app.ui.ViewModelFactoryTest
import ru.investlifestyle.app.ui.post.PostActivity
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
        loadingCategory(categoryId = args.categoryId, categoryType = args.categoryType)
        loadStateScreen()
        setAdapterClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindingAdapter() {
        adapterSubject = ThemePostsAdapter(requireContext())
        adapterSubject.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
        binding.rvSubject.adapter = adapterSubject
    }

    @SuppressLint("LogNotTimber")
    private fun loadingCategory(categoryId: Int, categoryType: String) {
        lifecycleScope.launch {
            viewModel.postsPagingDataCategory(categoryId, categoryType).collect { pagingData ->
                adapterSubject.submitData(lifecycle = lifecycle, pagingData = pagingData)
                binding.tvThemeHeader.text = args.categoryTitle
            }
        }
    }

    private fun loadStateScreen() {
        shimmerState(true)
        lifecycleScope.launch {
            adapterSubject.loadStateFlow.map {
                it.refresh
            }
                .distinctUntilChanged()
                .collect { loadState ->
                    when (loadState) {
                        is LoadState.Loading -> {
                            shimmerState(true)
                        }
                        is LoadState.NotLoading -> {
                            shimmerState(false)
                        }
                        is LoadState.Error -> {
                        }
                    }
                }
        }
    }

    private fun setAdapterClickListener() {
        adapterSubject.onPostClickListener = {
            val intent = PostActivity.intentPostActivity(requireContext(), it.id)
            startActivity(intent)
        }
    }

    private fun shimmerState(isShimmer: Boolean) {
        if (isShimmer) {
            binding.themeDetailShimmerLayout.isVisible = isShimmer
            binding.rvSubject.isVisible = false
            binding.appbarTheme.isVisible = false
        } else {
            binding.themeDetailShimmerLayout.isVisible = isShimmer
            binding.rvSubject.isVisible = true
            binding.appbarTheme.isVisible = true
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