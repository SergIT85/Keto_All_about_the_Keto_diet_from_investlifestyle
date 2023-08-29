package ru.investlifestyle.app.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.invoke
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

        if (_binding != null) {
            binding.homeFragmentRecycleViev.adapter = adapter
        }

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

    private fun loading() {
        try {
            lifecycleScope.launch {
                homeViewModel.posts.collect { pagingData ->
                    adapter.submitData(lifecycle = lifecycle, pagingData = pagingData)
                }
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
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
            if (_binding != null) {
                binding.mainDetailShimmerLayout.isVisible = isShimmer
                binding.homeFragmentRecycleViev.isVisible = false
                binding.appbar.isVisible = false
            }
        } else {
            if (_binding != null) {
                binding.mainDetailShimmerLayout.isVisible = isShimmer
                binding.mainCoordinatorLayout.isVisible = true
                binding.homeFragmentRecycleViev.isVisible = true
                binding.appbar.isVisible = true
            }
        }
    }
}