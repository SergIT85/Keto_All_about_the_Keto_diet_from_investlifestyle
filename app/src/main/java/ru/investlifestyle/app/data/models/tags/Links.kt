package ru.investlifestyle.app.data.models.tags

data class Links(
    val about: List<About>,
    val collection: List<Collection>,
    val curies: List<Cury>,
    val self: List<Self>,
    val wppost_type: List<WpPostType>
)