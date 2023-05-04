package ru.investlifestyle.app.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.investlifestyle.app.App
import ru.investlifestyle.app.data.networkApi.Content
import ru.investlifestyle.app.databinding.FragmentHomeBinding
import ru.investlifestyle.app.ui.ViewModelFactoryTest
import ru.investlifestyle.app.ui.home.adapter.HomePostsAdapter
import ru.investlifestyle.app.ui.post.PostActivity
import javax.inject.Inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: HomePostsAdapter

    private val component by lazy {
        (requireActivity().application as App).daggerAppComponent
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactoryTest

    // This property is only valid between onCreateView and
    // onDestroyView.
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
        val homeViewModel =
            ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)



        adapter = HomePostsAdapter(requireContext())

        binding.homeFragmentRecycleViev.adapter = adapter
        lifecycleScope.launch {
            // REFACTOR in sealed class!!!!
            shimmerState(true)
            delay(1000)
            shimmerState(false)
            homeViewModel.postsListViewModel.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })
        }
        setClickListener()

    }

    fun setClickListener() {
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

    private fun shimmerState(isShimmer: Boolean) {
        if(isShimmer) {
            main_detailShimmerLayout.isVisible = isShimmer
        } else {
            main_detailShimmerLayout.isVisible = isShimmer
            main_coordinatorLayout.isVisible = true
        }

    }
}