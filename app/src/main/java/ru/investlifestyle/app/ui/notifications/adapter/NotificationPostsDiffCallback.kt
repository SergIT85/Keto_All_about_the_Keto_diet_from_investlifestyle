package ru.investlifestyle.app.ui.notifications.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.investlifestyle.app.ui.models.PostUiModel

object NotificationPostsDiffCallback : DiffUtil.ItemCallback<PostUiModel>() {
    override fun areItemsTheSame(oldItem: PostUiModel, newItem: PostUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostUiModel, newItem: PostUiModel): Boolean {
        return oldItem == newItem
    }
}