package ru.investlifestyle.app.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.investlifestyle.app.ui.models.PostUiModel

object HomePostsDiffCallback : DiffUtil.ItemCallback<PostUiModel>() {
    override fun areItemsTheSame(oldItem: PostUiModel, newItem: PostUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostUiModel, newItem: PostUiModel): Boolean {
        return oldItem == newItem
    }
}