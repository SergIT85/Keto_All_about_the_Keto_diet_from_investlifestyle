package ru.investlifestyle.app.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.investlifestyle.app.App
import ru.investlifestyle.app.databinding.FragmentHomeBinding
import ru.investlifestyle.app.ui.ViewModelFactoryTest
import ru.investlifestyle.app.ui.home.adapter.HomePostsAdapter
import ru.investlifestyle.app.ui.post.PostActivity

@ExperimentalPagingApi
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: HomePostsAdapter

    private val component by lazy {
        (requireActivity().application as App).daggerAppComponent
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactoryTest

    private lateinit var homeViewModel: HomeViewModel
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel =
            ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        adapter = HomePostsAdapter(requireContext())
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.ALLOW

        binding.homeFragmentRecycleViev.adapter = adapter

        loading()
        loadListPosts()
        setClickListener()
        getQuotes()
    }

    private fun loadListPosts() {
        lifecycleScope.launch {

            adapter.loadStateFlow.map {
                it.refresh
            }
                .distinctUntilChanged()
                .collect { LoadState ->
                    when (LoadState) {
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

    private fun loading() {
        lifecycleScope.launch {
            homeViewModel.posts.collect { pagingData ->
                adapter.submitData(lifecycle = lifecycle, pagingData = pagingData)
            }
        }
    }

    private fun setClickListener() {
        adapter.onPostClickListener = {
            val intent = PostActivity.intentPostActivity(requireContext(), it.id)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val DEFAULT_ID = 0
        private const val POST_ID = "post_id"

        fun newInstanceHomeFragment(): HomeFragment {
            return HomeFragment()
        }
    }

    private fun getQuotes() {
        homeViewModel.quotes.observe(
            viewLifecycleOwner,
            Observer {
                binding.tvThoughtMain.text = it
            }
        )
    }

    private fun shimmerState(isShimmer: Boolean) {
        if (isShimmer) {
            main_detailShimmerLayout.isVisible = isShimmer
            home_fragment_recycle_viev.isVisible = false
            appbar.isVisible = false
        } else {
            main_detailShimmerLayout.isVisible = isShimmer
            main_coordinatorLayout.isVisible = true
            home_fragment_recycle_viev.isVisible = true
            appbar.isVisible = true
        }
    }
}