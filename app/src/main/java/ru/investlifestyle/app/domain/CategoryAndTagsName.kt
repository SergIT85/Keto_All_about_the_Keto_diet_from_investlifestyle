package ru.investlifestyle.app.domain

enum class CategoryAndTagsName(
    var typeCategory: String,
    var idCategory: Int,
    var titleCategory: String
) {
    LIKEPOSTS("categories", 0, "Сохранённые"),
    HEALTH("categories", 11, "Здоровье"),
    KETOCOURSES("categories", 188, "Кето курс"),
    NUTRITION("categories", 12, "Питание"),
    EVOLUTION("categories", 20, "Развитие"),
    TAGSKETO("tags", 27, "Кето"),
    TAGSEDUCATION("tags", 22, "Обучение"),
    TAGSUSEFUL("tags", 163, "Полезное"),
    TAGSRECIPES("tags", 39, "Рецепты");
}