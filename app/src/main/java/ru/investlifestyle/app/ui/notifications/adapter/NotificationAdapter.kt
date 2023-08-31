package ru.investlifestyle.app.ui.notifications.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facebook.stetho.common.Util
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_subject_post.view.imgPostSubject
import kotlinx.android.synthetic.main.item_subject_post.view.tvItemSubjectPost
import ru.investlifestyle.app.databinding.ItemSubjectPostBinding
import ru.investlifestyle.app.ui.models.PostUiModel

class NotificationAdapter(private val context: Context) : ListAdapter<
    PostUiModel,
    NotificationAdapter.NotificationVH
    >(NotificationPostsDiffCallback) {

    var onPostClickListener: ((PostUiModel) -> Util)? = null
    inner class NotificationVH(
        binding: ItemSubjectPostBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationVH {
        val binding = ItemSubjectPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NotificationVH(binding)
    }

    override fun onBindViewHolder(holder: NotificationVH, position: Int) {
        val item = getItem(position)

        if (item != null) {
            with(item) {
                with(holder.itemView) {
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