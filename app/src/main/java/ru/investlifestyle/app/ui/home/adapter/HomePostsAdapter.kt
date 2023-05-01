package ru.investlifestyle.app.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_home_article.view.*
import ru.investlifestyle.app.data.networkApi.Content
import ru.investlifestyle.app.databinding.ItemHomeArticleBinding
import ru.investlifestyle.app.ui.models.PostUiModel


class HomePostsAdapter(private val content: Content) : ListAdapter<PostUiModel,
            HomePostsAdapter.HomePostViewHolder>(HomePostsDiffCallback) {

    var onPostClickListener: OnPostClickListener? = null

    inner class HomePostViewHolder(binding: ItemHomeArticleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePostViewHolder {

        val binding = ItemHomeArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomePostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomePostViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.itemView) {
            with(item) {
                tvItemHomePost.text = title
                Picasso.get().load(posterMediaLinkUrl).into(ivItemPost)
                rootView.setOnClickListener {
                    onPostClickListener?.onPostClick(this)
                }
            }
        }
    }

    interface OnPostClickListener {
        fun onPostClick(postUiModel: PostUiModel)
    }
}