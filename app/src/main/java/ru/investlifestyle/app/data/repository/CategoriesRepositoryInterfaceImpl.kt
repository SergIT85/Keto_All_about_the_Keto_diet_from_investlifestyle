package ru.investlifestyle.app.data.repository

import android.app.Application
import javax.inject.Inject
import kotlin.random.Random
import kotlinx.coroutines.flow.flow
import ru.investlifestyle.app.R
import ru.investlifestyle.app.data.PostMapper
import ru.investlifestyle.app.data.mappers.toDomain
import ru.investlifestyle.app.data.room.ChoiceSubjectDaoRoom
import ru.investlifestyle.app.domain.CategoryAndTagsName
import ru.investlifestyle.app.domain.models.SaveCategories
import ru.investlifestyle.app.domain.repository.CategoriesRepositoryInterface

class CategoriesRepositoryInterfaceImpl @Inject constructor(
    private val subjectDaoRoom: ChoiceSubjectDaoRoom,
    private val application: Application,
    private val mapper: PostMapper
) : CategoriesRepositoryInterface {
    override fun getCategories() = flow<List<SaveCategories>> {
        subjectDaoRoom.getAllSubject().collect { listCategory ->
            val list = mapper.mapListChoiceSubjectEntityToListSubjectSaveCategories(listCategory)
            emit(
                list.map { it ->
                    (it.toDomain())
                }
            )
        }
    }

    override fun getQuotes(): String {
        val randomString = Random(System.currentTimeMillis())
        val array = application.resources.getStringArray(R.array.quotes)
        return array[randomString.nextInt(array.size)]
    }

    override suspend fun updateSubject(selected: Boolean, idCategory: Int) {
        subjectDaoRoom.updateSubject(selected, idCategory)
    }

    // Will be fixed for requests from API when the backing is ready
    override suspend fun fillingDbInit() {

        val categoryHealth = SaveCategories(
            CategoryAndTagsName.HEALTH.titleCategory,
            CategoryAndTagsName.HEALTH.typeCategory,
            CategoryAndTagsName.HEALTH.idCategory
        )
        val categoryKetoCourses = SaveCategories(
            CategoryAndTagsName.KETOCOURSES.titleCategory,
            CategoryAndTagsName.KETOCOURSES.typeCategory,
            CategoryAndTagsName.KETOCOURSES.idCategory
        )
        val categoryNutrition = SaveCategories(
            CategoryAndTagsName.NUTRITION.titleCategory,
            CategoryAndTagsName.NUTRITION.typeCategory,
            CategoryAndTagsName.NUTRITION.idCategory,
            false
        )
        val categoryEvolution = SaveCategories(
            CategoryAndTagsName.EVOLUTION.titleCategory,
            CategoryAndTagsName.EVOLUTION.typeCategory,
            CategoryAndTagsName.EVOLUTION.idCategory,
            false
        )
        val tagsKeto = SaveCategories(
            CategoryAndTagsName.TAGSKETO.titleCategory,
            CategoryAndTagsName.TAGSKETO.typeCategory,
            CategoryAndTagsName.TAGSKETO.idCategory,
            false
        )
        val tagsEducation = SaveCategories(
            CategoryAndTagsName.TAGSEDUCATION.titleCategory,
            CategoryAndTagsName.TAGSEDUCATION.typeCategory,
            CategoryAndTagsName.TAGSEDUCATION.idCategory,
            false
        )
        val tagsUseful = SaveCategories(
            CategoryAndTagsName.TAGSUSEFUL.titleCategory,
            CategoryAndTagsName.TAGSUSEFUL.typeCategory,
            CategoryAndTagsName.TAGSUSEFUL.idCategory,
            false
        )
        val tagsRecipes = SaveCategories(
            CategoryAndTagsName.TAGSRECIPES.titleCategory,
            CategoryAndTagsName.TAGSRECIPES.typeCategory,
            CategoryAndTagsName.TAGSRECIPES.idCategory
        )
        val likePosts = SaveCategories(
            CategoryAndTagsName.LIKEPOSTS.titleCategory,
            CategoryAndTagsName.LIKEPOSTS.typeCategory,
            CategoryAndTagsName.LIKEPOSTS.idCategory
        )

        val list = listOf(
            categoryHealth, categoryKetoCourses, categoryNutrition, categoryEvolution,
            tagsKeto, tagsEducation, tagsUseful, tagsRecipes, likePosts
        )
        if (subjectDaoRoom.isEmpty()) {
            subjectDaoRoom.save(mapper.mapListSubjectCategoryToListSubjectEntity(list))
        }
    }
}