package ru.investlifestyle.app.ui.post

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import ru.investlifestyle.app.App
import ru.investlifestyle.app.R
import ru.investlifestyle.app.databinding.ActivityPostBinding
import ru.investlifestyle.app.databinding.FragmentPostBinding
import ru.investlifestyle.app.ui.ViewModelFactoryTest
import javax.inject.Inject


class PostFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as App).daggerAppComponent
    }

    private var postId = DEFAULT_ID
    private var _binding: FragmentPostBinding? = null
    private val binding: FragmentPostBinding
        get() = _binding ?: throw RuntimeException("FragmentPostBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactoryTest

    private lateinit var viewModel: PostViewModel

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)

    }

    override fun onStart() {
        super.onStart()
        launchPost()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PostViewModel::class.java)
        requirePost(postId)

    }

    private fun requirePost(postId: Int) {
        viewModel.getPostById(postId)
    }


    private fun launchPost() {
        lifecycleScope.launchWhenStarted {
            viewModel.onePostViewModel.collect{
                when(it) {
                    is LoadPostState.Load -> {
                        shimmerState(isShimmer = true)
                    }
                    //is LoadPostState.Load -> imageView.isGone
                    is LoadPostState.Loaded -> {
                        shimmerState(isShimmer = false)
                        val htmlContent =
                            "<!DOCTYPE html> <html> <head> </head><meta name= viewport content= " +
                                    "width=device-width  initial-scale=1.0 > <style>img{display: " +
                                    "inline;height: auto;max-width: 100%;} video{display: " +
                                    "inline;width: 100%;poster=} p{height: auto;width: 100%; } " +
                                    "iframe{width: 100%} </style> <body>   " +
                                    "${it.post.content.replace("\"","")} " +
                                    "</body></html>"
                         binding.wvPost.loadDataWithBaseURL(
                                null,
                                htmlContent,
                                "text/html; charset=utf-8",
                                "UTF-8",
                                null
                            )
                        binding.textView.text = it.post.title
                        Picasso.get().load(it.post.posterMediaLinkUrl).into(binding.appBarImage)



                    }
                    else -> {/*throw RuntimeException("private fun launchPost() not Worked MY EXCEPTION!!!")*/}
                }



            }
        }

    }

    private fun parseParams() {
        val args = requireArguments()

        if (!args.containsKey(POST_ID)) {
            throw RuntimeException("Parse in intent No POST ID in Arguments MAY EXCEPTION!!!!!!!!")
        }
        postId = args.getInt(POST_ID, DEFAULT_ID)
        Log.d("parseParams", "arguments =${postId}")
    }

    companion object {
        private const val DEFAULT_ID = 0
        private const val POST_ID = "post_id"

        fun newInstancePostFragment(postId: Int): PostFragment {
            return PostFragment().apply {
                arguments = Bundle().apply {
                    Log.d("newInstancePostFragment", "arguments =${postId}")
                    putInt(POST_ID, postId)
                }
            }
        }
    }

    private fun shimmerState(isShimmer: Boolean) {
        if(isShimmer) {
            detailShimmerLayout.isVisible = isShimmer
        } else {
            detailShimmerLayout.isVisible = isShimmer
            coordinatorLayout.isVisible = true
        }
    }

}