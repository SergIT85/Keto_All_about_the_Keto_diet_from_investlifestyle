package ru.investlifestyle.app.data.mappers

fun getImageUrlFromYoastHead(string: String): String {
    val listUrls = performRegex(string)
    var imageUrl = string
    listUrls.map {
        if (it.contains(IMAGER_URL_REGEX, ignoreCase = true)) {
            imageUrl = it
        }
    }
    return imageUrl
}

private fun performRegex(text: String): List<String> {
    val regPattern = Regex(REGEX_URL)
    val list = regPattern.findAll(text.toString()).map { it.value }.toList()
    return list
}

const val REGEX_URL = """(?:https?|ftp)://\S+"""
const val IMAGER_URL_REGEX = "wp-content"
