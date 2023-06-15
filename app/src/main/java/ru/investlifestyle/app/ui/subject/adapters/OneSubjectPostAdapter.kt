package ru.investlifestyle.app.ui.subject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facebook.stetho.common.Util
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_home_article.view.*
import kotlinx.android.synthetic.main.item_subject_post.view.*
import ru.investlifestyle.app.R
import ru.investlifestyle.app.databinding.ItemSubjectPostBinding
import ru.investlifestyle.app.ui.models.PostUiModel

class OneSubjectPostAdapter(
    private val postUiModel: List<PostUiModel>
) : ListAdapter<PostUiModel, OneSubjectPostAdapter.OneSubjectPostViewHolder>(OneSubjectDiffCallback) {

    var onPostClickListener: ((PostUiModel) -> Util)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OneSubjectPostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_subject_post, parent, false)
        return OneSubjectPostViewHolder(view)
    }

    override fun onBindViewHolder(holder: OneSubjectPostViewHolder, position: Int) {
        holder.binding.apply {
            tvItemSubjectPost.text = postUiModel[position].title
            Picasso.get().load(postUiModel[position].posterMediaLinkUrl).into(imgPostSubject)
        }
    }

    inner class OneSubjectPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemSubjectPostBinding.bind(itemView)
    }
}