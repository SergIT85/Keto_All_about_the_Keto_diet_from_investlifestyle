package ru.investlifestyle.app.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.investlifestyle.app.R

class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PostFragment.newInstance())
                .commitNow()
        }
    }
}