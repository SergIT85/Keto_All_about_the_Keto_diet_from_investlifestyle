package ru.investlifestyle.app.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.investlifestyle.app.data.networkApi.examin.Repo
import ru.investlifestyle.app.databinding.FragmentDashboardBinding

class TopicsFragment : Fragment() {



    lateinit var topicsViewModel: TopicsViewModel

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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

        val repo = Repo()
        val topicsViewModelFactoryTest = ViewModelFactoryTest(repo)
        topicsViewModel = ViewModelProvider(this,
            topicsViewModelFactoryTest)[TopicsViewModel::class.java]
        topicsViewModel.postListViewModel.observe(this, Observer { list ->
            textView.text = list[0].title.rendered
        })

    }
}