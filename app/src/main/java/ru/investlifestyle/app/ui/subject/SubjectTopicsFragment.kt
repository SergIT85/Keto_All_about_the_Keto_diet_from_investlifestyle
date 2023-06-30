package ru.investlifestyle.app.ui.subject

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import ru.investlifestyle.app.App
import ru.investlifestyle.app.databinding.FragmentSubjectTopicsBinding
import ru.investlifestyle.app.ui.ViewModelFactoryTest
import ru.investlifestyle.app.ui.subject.adapters.SubjectPostsAdapter
import javax.inject.Inject

class SubjectTopicsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactoryTest: ViewModelFactoryTest


    @ExperimentalPagingApi
    private val component by lazy {
        (requireActivity().application as App).daggerAppComponent
    }

    lateinit var adapter: SubjectPostsAdapter
    lateinit var adapterTags: SubjectPostsAdapter
    lateinit var adapterTags2: SubjectPostsAdapter
    lateinit var adapterTags3: SubjectPostsAdapter

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
        subjectTopicsViewModel.loadHealthPost.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            adapterTags3.submitList(it)
            binding.tvHealthCategories.text = "Здоровье"

        }

        subjectTopicsViewModel.loadSubjectTagsPost.observe(viewLifecycleOwner) {
            adapterTags.submitList(it)
            adapterTags2.submitList(it)
        }



        //val textView: TextView = binding.textDashboard

        /*adapter = OneSubjectPostAdapter(MockData.collection)
        binding.rvHealthCategories.adapter = adapter
        subjectTopicsViewModel.loadSubjectPost.observe(viewLifecycleOwner) {
            adapter = OneSubjectPostAdapter(it)
        }*/



       /* subjectTopicsViewModel = ViewModelProvider(this,
            viewModelFactoryTest)[SubjectTopicsViewModel::class.java]
        subjectTopicsViewModel.postListViewModel.observe(this, Observer { list ->
            //textView.text = list[0].title.rendered
        })*/

    }
    private fun bindingAdapter() {
        adapter = SubjectPostsAdapter(requireContext())
        binding.rvHealthCategories.adapter = adapter

        adapterTags = SubjectPostsAdapter((requireContext()))
        binding.rvKetoCourses.adapter = adapterTags

        adapterTags2 = SubjectPostsAdapter((requireContext()))
        binding.rvKetoEducation.adapter = adapterTags2

        adapterTags3 = SubjectPostsAdapter((requireContext()))
        binding.rvNutritionCategories.adapter = adapterTags3
    }
}