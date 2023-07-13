package ru.investlifestyle.app.ui.subject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_subject_post.view.*
import ru.investlifestyle.app.databinding.ItemSubjectPostBinding
import ru.investlifestyle.app.ui.models.PostUiModel

class SubjectPostsAdapter(private val context: Context) : ListAdapter<
    PostUiModel,
    SubjectPostsAdapter.SubjectPostsViewHolder
    >(SubjectDiffCallback) {

    var onPostClickListener: ((PostUiModel) -> Unit)? = null

    inner class SubjectPostsViewHolder(
        binding: ItemSubjectPostBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectPostsViewHolder {
        val binding = ItemSubjectPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SubjectPostsViewHolder((binding))
    }

    override fun onBindViewHolder(holder: SubjectPostsViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            with(holder.itemView) {
                with(item) {
                    tvItemSubjectPost.text = title
                    Picasso.get().load(posterMediaLinkUrl).into(imgPostSubject)
                    rootView.setOnClickListener {
                        onPostClickListener?.invoke(item)
                    }
                }
            }
        }
    }
}