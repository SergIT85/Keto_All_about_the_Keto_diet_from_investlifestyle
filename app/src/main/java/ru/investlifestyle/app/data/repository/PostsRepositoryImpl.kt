package ru.investlifestyle.app.data.repository

import android.annotation.SuppressLint
import android.app.Application
import androidx.paging.*
import javax.inject.Inject
import kotlin.random.Random
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.investlifestyle.app.R
import ru.investlifestyle.app.data.PostMapper
import ru.investlifestyle.app.data.models.categories.SaveCategories
import ru.investlifestyle.app.data.networkApi.PostsApiInterface
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.data.networkApi.examin.Repo
import ru.investlifestyle.app.data.paging.PostPagingRemoteMediator
import ru.investlifestyle.app.data.room.PostDaoRoom
import ru.investlifestyle.app.domain.PostRepositoryInterface
import ru.investlifestyle.app.ui.models.PostUiModel

@ExperimentalPagingApi
class PostsRepositoryImpl @Inject constructor(
    private val apiClient: PostsApiInterface,
    private val mapper: PostMapper,
    private val application: Application,
    private val postDaoRoom: PostDaoRoom,
) : PostRepositoryInterface {

    private val postPagingRemoteMediator = PostPagingRemoteMediator(postDaoRoom, apiClient, mapper)

    private val service = Repo()
    override fun getPostPagingSource(): Flow<PagingData<PostUiModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 1,
                enablePlaceholders = true
            ),
            remoteMediator = postPagingRemoteMediator,
            pagingSourceFactory = {
                postDaoRoom.getPostListPagingSource()
            }
        ).flow
            .map { pagingDate ->
                pagingDate.map { mapper.mapPostDbModelToPostUiModel(it) }
            }
    }

    override suspend fun getPostsList(postsCount: Int): List<PostsModelDataItem> {
        return service.getPost(1)
    }

    override suspend fun getMainPostList(page: Int): List<PostUiModel> {
        return mapper.mapListPostDataToListPostUi(apiClient.getPostsList(page))
    }

    // исправить на загрузку из БД!!!! когда будет создана
    override suspend fun loadOnePost(postId: Int): PostUiModel =
        mapper.mapPostModelDataToPostUiModel(apiClient.loadOnePostById(postId))

    override suspend fun loadSubjectPosts(
        categories: Int,
        page: Int,
        perPage: Int,
        embed: Boolean
    ): List<PostUiModel> {
        return mapper.mapListPostDataToListPostUi(
            apiClient.loadSubjectPosts(categories, page, perPage, embed)
        )
    }

    override suspend fun loadSubjectPostsFlow(
        categories: Int,
        page: Int,
        perPage: Int,
        embed: Boolean
    ): List<PostUiModel> =
        mapper.mapListPostDataToListPostUi(
            apiClient.loadSubjectPosts(categories, page, perPage, embed)
        )

    override suspend fun loadSubjectTagsPosts(
        tags: Int,
        page: Int,
        perPage: Int,
        embed: Boolean
    ): List<PostUiModel> {
        return mapper.mapListPostDataToListPostUi(
            apiClient.loadSubjectTagsPosts(tags, page, perPage, embed)
        )
    }

    // Will be fixed for requests from API when the backing is ready
    override suspend fun getCategories(): List<SaveCategories> {
        val categoryHealth = SaveCategories(
            HEALTH,
            CATEGORIES,
            IDHEALTH
        )
        val categoryKetoCourses = SaveCategories(
            KETOCOURSES,
            CATEGORIES,
            IDKETOCOURSES
        )
        val categoryNutrition = SaveCategories(
            NUTRITION,
            CATEGORIES,
            IDNUTRITION
        )
        val categoryEvolution = SaveCategories(
            EVOLUTION,
            CATEGORIES,
            IDEVOLUTION
        )
        val tagsKeto = SaveCategories(
            TAGSKETO,
            TAGS,
            IDTAGSKETO
        )
        val tagsEducation = SaveCategories(
            TAGSEDUCATION,
            TAGS,
            IDTAGSEDUCATION
        )
        val tagsUseful = SaveCategories(
            TAGSUSEFUL,
            TAGS,
            IDTAGSUSEFUL
        )
        val tagsRecipes = SaveCategories(
            TAGSRECIPES,
            TAGS,
            IDTAGSRECIPES
        )
        return listOf(
            categoryHealth, categoryKetoCourses, categoryNutrition, categoryEvolution,
            tagsKeto, tagsEducation, tagsUseful, tagsRecipes
        )
    }

    @SuppressLint("LogNotTimber")
    override fun getQuotes(): String {
        val randomString = Random(System.currentTimeMillis())
        val array = application.resources.getStringArray(R.array.quotes)
        return array[randomString.nextInt(array.size)]
    }

    companion object {
        const val HEALTH = "Здоровье"
        const val KETOCOURSES = "Кето курс"
        const val NUTRITION = "Питание"
        const val EVOLUTION = "Развитие"
        const val TAGSKETO = "Кето"
        const val TAGSEDUCATION = "Обучение"
        const val TAGSUSEFUL = "Полезное"
        const val TAGSRECIPES = "Рецепты"

        const val IDHEALTH = 11
        const val IDKETOCOURSES = 188
        const val IDNUTRITION = 12
        const val IDEVOLUTION = 20
        const val IDTAGSKETO = 27
        const val IDTAGSEDUCATION = 22
        const val IDTAGSUSEFUL = 163
        const val IDTAGSRECIPES = 39

        const val CATEGORIES = "categories"
        const val TAGS = "tags"
    }
}