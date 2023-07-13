package ru.investlifestyle.app.ui.post

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.ExperimentalPagingApi
import kotlinx.android.synthetic.main.activity_post.*
import ru.investlifestyle.app.App
import ru.investlifestyle.app.databinding.ActivityPostBinding

@ExperimentalPagingApi
class PostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostBinding

    private var postId = DEFAULT_ID

    private val component by lazy {
        (application as App).daggerAppComponent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseIntent()
        if (savedInstanceState == null) {
            Log.d("intentPostActivity", "arguments =$postId")
            launchPostFragment(postId)
        }
    }

    private fun launchPostFragment(postId: Int) {
        if (postId != DEFAULT_ID) {
            val fragment = PostFragment.newInstancePostFragment(postId)
            supportFragmentManager.beginTransaction()
                .replace(fragmentPostsContainer.id, fragment)
                .commit()
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(POST_ID)) {
            throw RuntimeException("Parse in intent PostId is absent MY EXCEPTION!!!")
        }
        postId = intent.getIntExtra(POST_ID, DEFAULT_ID)
    }
    companion object {
        private const val DEFAULT_ID = 0
        private const val POST_ID = "post_id"

        fun intentPostActivity(context: Context, postId: Int): Intent {
            val intent = Intent(context, PostActivity::class.java)
            intent.putExtra(POST_ID, postId)
            Log.d("intentPostActivity", "arguments =$postId")
            return intent
        }
    }
}