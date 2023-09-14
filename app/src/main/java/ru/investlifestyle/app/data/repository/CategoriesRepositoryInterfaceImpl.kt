package ru.investlifestyle.app.data.repository

import android.app.Application
import javax.inject.Inject
import kotlin.random.Random
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.investlifestyle.app.R
import ru.investlifestyle.app.data.PostMapper
import ru.investlifestyle.app.data.mappers.toDomain
import ru.investlifestyle.app.data.room.ChoiceSubjectDaoRoom
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

        /*subjectDaoRoom.getAllSubject().map { it ->
            val mapper = mapper.mapListChoiceSubjectEntityToListSubjectSaveCategories(it)
            (
                mapper.map { saveCategoriesData ->
                    saveCategoriesData.toDomain()
                }
                )
        }*/
    }

    override fun getCategoriesForChoiceFragment(): Flow<List<SaveCategories>> {
        TODO("Not yet implemented")
    }

    override fun getQuotes(): String {
        val randomString = Random(System.currentTimeMillis())
        val array = application.resources.getStringArray(R.array.quotes)
        return array[randomString.nextInt(array.size)]
    }

    override suspend fun updateSubject(selected: Boolean, idCategory: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getSingleSubjectById(idCategories: Int): SaveCategories {
        TODO("Not yet implemented")
    }

    override suspend fun fillingDbInit() {
        TODO("Not yet implemented")
    }
}