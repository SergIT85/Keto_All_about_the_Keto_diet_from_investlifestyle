package ru.investlifestyle.app.data.dto

import com.google.gson.annotations.SerializedName

data class PostApi(
    val id: Int,
    val date: String,
    @SerializedName("date_gmt")
    val dateGmt: String,
    val modified: String,
    val slug: String,
    val status: String,
    val type: String,
    val link: String,
    val title: List<TitleApi>,
    val content: List<ContentApi>
)
