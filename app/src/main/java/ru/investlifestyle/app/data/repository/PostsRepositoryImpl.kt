package ru.investlifestyle.app.data.repository

import android.annotation.SuppressLint
import android.app.Application
import androidx.paging.*
import javax.inject.Inject
import kotlin.random.Random
import kotlinx.coroutines.flow.*
import ru.investlifestyle.app.R
import ru.investlifestyle.app.data.PostMapper
import ru.investlifestyle.app.data.models.categories.SaveCategories
import ru.investlifestyle.app.data.networkApi.PostsApiInterface
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.data.networkApi.examin.Repo
import ru.investlifestyle.app.data.paging.PostPagingRemoteMediator
import ru.investlifestyle.app.data.paging.PostPagingSource
import ru.investlifestyle.app.data.paging.PostTagsPagingSource
import ru.investlifestyle.app.data.room.ChoiceSubjectDaoRoom
import ru.investlifestyle.app.data.room.LikePostsDaoRoom
import ru.investlifestyle.app.data.room.PostDaoRoom
import ru.investlifestyle.app.data.room.UserNameDaoRoom
import ru.investlifestyle.app.domain.CategoryAndTagsName
import ru.investlifestyle.app.domain.PostRepositoryInterface
import ru.investlifestyle.app.ui.models.PostUiModel
import ru.investlifestyle.app.ui.models.UserName

@ExperimentalPagingApi
class PostsRepositoryImpl @Inject constructor(
    private val apiClient: PostsApiInterface,
    private val mapper: PostMapper,
    private val application: Application,
    private val postDaoRoom: PostDaoRoom,
    private val subjectDaoRoom: ChoiceSubjectDaoRoom,
    private val likePostsDaoRoom: LikePostsDaoRoom,
    private val userNameDaoRoom: UserNameDaoRoom
) : PostRepositoryInterface {

    private val postPagingRemoteMediator = PostPagingRemoteMediator(postDaoRoom, apiClient, mapper)

    private val service = Repo()
    override suspend fun getUserName(): UserName {
        return mapper.mapUserNameEntityToUserName(userNameDaoRoom.getUserName())
    }

    override suspend fun saveUserName(userName: UserName) {
        userNameDaoRoom.save(mapper.mapUserNameToUserNameEntity(userName))
    }

    override suspend fun userNameIsEmpty(): Boolean {
        return userNameDaoRoom.isEmpty()
    }

    override fun getPostPagingRemoteMediator(): Flow<PagingData<PostUiModel>> {
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

    override fun getPostPagingSource(categoryId: Int) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 30,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            PostPagingSource(
                categoryId = categoryId,
                apiClient = apiClient,
                mapper = mapper
            )
        }
    ).flow

    override fun getPostTagsPagingSource(tagsId: Int) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 30,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            PostTagsPagingSource(
                tagsId = tagsId,
                apiClient = apiClient,
                mapper = mapper
            )
        }
    ).flow

    override suspend fun getPostsList(postsCount: Int): List<PostsModelDataItem> {
        return service.getPost(1)
    }

    override suspend fun getMainPostList(page: Int): List<PostUiModel> {
        return mapper.mapListPostDataToListPostUi(apiClient.getPostsList(page))
    }

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

    override fun getCategories(): Flow<List<SaveCategories>> {
        return subjectDaoRoom.getAllSubject().map {
            mapper.mapListChoiceSubjectEntityToListSubjectSaveCategories(it)
        }
    }

    override fun getCategoriesForChoiceFragment(): Flow<List<SaveCategories>> {
        return subjectDaoRoom.getAllSubjectForChoiceModel().map {
            mapper.mapListChoiceSubjectEntityToListSubjectSaveCategories(it)
        }
    }

    @SuppressLint("LogNotTimber")
    override fun getQuotes(): String {
        val randomString = Random(System.currentTimeMillis())
        val array = application.resources.getStringArray(R.array.quotes)
        return array[randomString.nextInt(array.size)]
    }

    override suspend fun updateSubject(selected: Boolean, idCategory: Int) {
        subjectDaoRoom.updateSubject(selected, idCategory)
    }

    override suspend fun getSingleSubjectById(idCategories: Int): SaveCategories {
        return mapper.mapChoiceSubjectEntityToSubjectSaveCategories(
            subjectDaoRoom.getSingleSubjectById(subjectId = idCategories)
        )
    }

    override suspend fun likePostIsEmpty(): Boolean = userNameDaoRoom.isEmpty()

    override suspend fun getLikePostById(postId: Int): PostUiModel {
        return mapper.mapLikePostDbModelToPostUiModel(likePostsDaoRoom.getLikePostById(postId))
    }

    override suspend fun getAllLikePosts(): List<PostUiModel> {
        return mapper.mapListLikePostBdModelToListPostUiModel(likePostsDaoRoom.getAllLikePosts())
    }

    override suspend fun insertLikePost(likePost: PostUiModel) {
        likePostsDaoRoom.insertLikePost(mapper.mapPostUiModelToLikePostDbModel(likePost))
    }

    override suspend fun deleteLikePostById(id: Int) {
        likePostsDaoRoom.deleteLikePostById(id)
    }

    override suspend fun getLikePostByIdBoolean(id: Int): Boolean {
        return likePostsDaoRoom.getLikePostByIdBoolean(id)
    }
}