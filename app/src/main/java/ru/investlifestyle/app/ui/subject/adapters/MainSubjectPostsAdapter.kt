package ru.investlifestyle.app.ui.subject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.investlifestyle.app.R
import ru.investlifestyle.app.data.models.SubjectTopicsModel
import ru.investlifestyle.app.databinding.SubjectPostsPartntBinding

class MainSubjectPostsAdapter(
    private val collection: List<SubjectTopicsModel>
) : RecyclerView.Adapter<MainSubjectPostsAdapter.MainSubjectPostsViewHolder>() {

    inner class MainSubjectPostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = SubjectPostsPartntBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainSubjectPostsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.subject_posts_partnt, parent, false)
        return MainSubjectPostsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainSubjectPostsViewHolder, position: Int) {
        holder.binding.apply {
            val collection = collection[position]
            tvSubjectPosts.text = collection.subjectTitle
            val postAdapter = OneSubjectPostAdapter(collection.postsList)
            rvSubjectMain.adapter = postAdapter
        }
    }
    override fun getItemCount() = collection.size
}