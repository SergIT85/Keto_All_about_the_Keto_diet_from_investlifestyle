package ru.investlifestyle.app.ui.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import ru.investlifestyle.app.App
import ru.investlifestyle.app.data.networkApi.examin.Repo
import ru.investlifestyle.app.databinding.FragmentDashboardBinding
import ru.investlifestyle.app.ui.ViewModelFactoryTest
import ru.investlifestyle.app.ui.ViewModelFactoryTest_Factory
import javax.inject.Inject

class TopicsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactoryTest: ViewModelFactoryTest


    @ExperimentalPagingApi
    private val component by lazy {
        (requireActivity().application as App).daggerAppComponent
    }


    lateinit var topicsViewModel: TopicsViewModel

    private var _binding: FragmentDashboardBinding? = null

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

        /*val topicsViewModel =
            ViewModelProvider(this).get(TopicsViewModel::class.java)*/

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("CheckResult", "FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)



        val textView: TextView = binding.textDashboard



        topicsViewModel = ViewModelProvider(this,
            viewModelFactoryTest)[TopicsViewModel::class.java]
        topicsViewModel.postListViewModel.observe(this, Observer { list ->
            textView.text = list[0].title.rendered
        })

    }
}