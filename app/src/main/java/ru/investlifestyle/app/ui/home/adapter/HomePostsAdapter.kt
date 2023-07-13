package ru.investlifestyle.app.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_home_article.view.*
import ru.investlifestyle.app.databinding.ItemHomeArticleBinding
import ru.investlifestyle.app.ui.models.PostUiModel

class HomePostsAdapter(private val context: Context) : PagingDataAdapter<PostUiModel,
    HomePostsAdapter.HomePostViewHolder>(HomePostsDiffCallback) {

    var onPostClickListener: ((PostUiModel) -> Unit)? = null

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

        if (item != null)
            with(holder.itemView) {
                with(item) {
                    tvItemHomePost.text = title
                    Picasso.get().load(posterMediaLinkUrl).into(ivItemPost)
                    rootView.setOnClickListener {
                        onPostClickListener?.invoke(item)
                    }
                }
            }
    }

    interface OnPostClickListener {
        fun onPostClick(postUiModel: PostUiModel)
    }
}