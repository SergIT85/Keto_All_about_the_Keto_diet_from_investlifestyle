package ru.investlifestyle.app.data.models.categories

data class Links(
    val about: List<About>,
    val collection: List<Collection>,
    val curies: List<Cury>,
    val self: List<Self>,
    val up: List<Up>,
    val wppost_type: List<WpPostType>
)