package ru.investlifestyle.app.ui.models

data class PostUiModel(
    val id: Int,
    val link: String,
    val title: String,
    val posterMediaLinkUrl: String,
    val content: String,
    val protected: Boolean,
    val author: String,
    val categories: List<Int>,
    var liked: Boolean = false,
    val modifiedGmt: String
)
