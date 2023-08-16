package ru.investlifestyle.app.ui.theme.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_home_article.view.ivItemPost
import kotlinx.android.synthetic.main.item_home_article.view.tvItemHomePost
import ru.investlifestyle.app.databinding.ItemHomeArticleBinding
import ru.investlifestyle.app.ui.models.PostUiModel

class ThemePostsAdapter(
    private val context: Context
) : PagingDataAdapter<PostUiModel, ThemePostsAdapter.ThemePostsViewHolder>(ThemeDiffCallback) {

    var onPostClickListener: ((PostUiModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemePostsViewHolder {
        val binding = ItemHomeArticleBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ThemePostsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThemePostsViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
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
    }

    inner class ThemePostsViewHolder(
        itemView: ItemHomeArticleBinding
    ) : RecyclerView.ViewHolder(itemView.root)
}