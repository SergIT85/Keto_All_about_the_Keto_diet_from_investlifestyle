package ru.investlifestyle.app.ui.theme.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_home_article.view.ivItemPost
import kotlinx.android.synthetic.main.item_home_article.view.tvItemHomePost
import ru.investlifestyle.app.databinding.ItemHomeArticleBinding
import ru.investlifestyle.app.ui.models.PostUiModel

class ThemePostsAdapter(
    private val context: Context
) : ListAdapter<PostUiModel, ThemePostsAdapter.ThemePostsViewHolder>(ThemeDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemePostsViewHolder {
        val binding = ItemHomeArticleBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ThemePostsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThemePostsViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.itemView) {
            with(item) {
                tvItemHomePost.text = title
                Picasso.get().load(posterMediaLinkUrl).into(ivItemPost)
            }
        }
    }

    inner class ThemePostsViewHolder(
        itemView: ItemHomeArticleBinding
    ) : RecyclerView.ViewHolder(itemView.root)
}