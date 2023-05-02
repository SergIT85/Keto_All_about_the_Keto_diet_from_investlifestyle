package ru.investlifestyle.app.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.investlifestyle.app.App
import ru.investlifestyle.app.data.networkApi.Content
import ru.investlifestyle.app.databinding.FragmentHomeBinding
import ru.investlifestyle.app.ui.ViewModelFactoryTest
import ru.investlifestyle.app.ui.home.adapter.HomePostsAdapter
import javax.inject.Inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

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

        val adapter = context?.let { HomePostsAdapter(it.applicationContext) }

        binding.homeFragmentRecycleViev.adapter = adapter
        lifecycleScope.launch {
            homeViewModel.postsListViewModel.observe(viewLifecycleOwner, Observer {
                adapter?.submitList(it)
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}