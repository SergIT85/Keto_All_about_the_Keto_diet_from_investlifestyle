package ru.investlifestyle.app.data.models.tags

data class TagsItem(
    val _links: Links,
    val count: Int,
    val description: String,
    val id: Int,
    val link: String,
    val meta: List<Any>,
    val name: String,
    val slug: String,
    val taxonomy: String,
    val yoast_head: String
)