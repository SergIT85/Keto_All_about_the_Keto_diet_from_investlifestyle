package ru.investlifestyle.app.data.networkApi

class PostsModelData : ArrayList<PostsModelDataItem>() {
    val ppp: PostsModelDataItem? = null
}

data class PostsModelDataItem(
    val _links: Links,
    val author: Int,
    val categories: List<Int>,
    val comment_status: String,
    val content: Content,
    val date: String,
    val date_gmt: String,
    val excerpt: Excerpt,
    val featured_media: Int,
    val format: String,
    val guid: Guid,
    val id: Int,
    val link: String,
    val meta: List<Any>,
    val modified: String,
    val modified_gmt: String,
    val ping_status: String,
    val slug: String,
    val status: String,
    val sticky: Boolean,
    val tags: List<Int>,
    val template: String,
    val title: Title,
    val type: String,
    val yoast_head: String
)

data class Links(
    val about: List<About>,
    val author: List<Author>,
    val collection: List<Collection>,
    val curies: List<Cury>,
    /*val predecessor-version: List<PredecessorVersion>,
    val replies: List<Reply>,
    val self: List<Self>,
    val version-history: List<VersionHistory>,
    val wp:attachment: List<WpAttachment>,
    val wp:featuredmedia: List<WpFeaturedmedia>,
    val wp:term: List<WpTerm>*/
)

data class Content(
    val `protected`: Boolean,
    val rendered: String
)

data class Categories(
    val count: Int,
    val id: Int,
    val name: String,
    val slug: String
)

data class Excerpt(
    val `protected`: Boolean,
    val rendered: String
)

data class Guid(
    val rendered: String
)

data class Title(
    val rendered: String
)

data class About(
    val href: String
)

data class Author(
    val embeddable: Boolean,
    val href: String
)

data class Collection(
    val href: String
)

data class Cury(
    val href: String,
    val name: String,
    val templated: Boolean
)

data class PredecessorVersion(
    val href: String,
    val id: Int
)

data class Reply(
    val embeddable: Boolean,
    val href: String
)

data class Self(
    val href: String
)

data class VersionHistory(
    val count: Int,
    val href: String
)

data class WpAttachment(
    val href: String
)

data class WpFeaturedmedia(
    val embeddable: Boolean,
    val href: String
)

data class WpTerm(
    val embeddable: Boolean,
    val href: String,
    val taxonomy: String
)