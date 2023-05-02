package ru.investlifestyle.app.data.models.post

import ru.investlifestyle.app.data.networkApi.Content

//класс возможный для передачи моделек из дата слоя. буду использовать позже.
data class Post(
    private val content: Content
)
