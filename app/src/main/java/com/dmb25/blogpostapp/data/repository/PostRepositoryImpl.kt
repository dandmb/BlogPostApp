package com.dmb25.blogpostapp.data.repository

import com.dmb25.blogpostapp.data.local.dao.PostDao
import com.dmb25.blogpostapp.data.mapper.toDomain
import com.dmb25.blogpostapp.data.mapper.toDto
import com.dmb25.blogpostapp.data.mapper.toEntity
import com.dmb25.blogpostapp.data.remote.BlogApiService
import com.dmb25.blogpostapp.domain.model.Post
import com.dmb25.blogpostapp.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class PostRepositoryImpl(
    private val api: BlogApiService,
    private val postDao: PostDao
) : PostRepository {
    override fun getPostsByUser(userId: Int): Flow<List<Post>> {
        return postDao.getPostsByUserId(userId)
            .map { entities -> entities.map { it.toDomain() } }
            .onStart { refreshGetPostsByUser(userId) }
    }

    suspend fun refreshGetPostsByUser(userId: Int) {
        try {
            val dtos = api.getUserPosts(userId.toString())
            postDao.insertPosts(dtos.map { it.toEntity() })
        } catch (e: Exception) {

        }
    }

    override fun getPostById(id: Int): Flow<Post?> {
        return postDao.getPostById(id)
            .map { it?.toDomain() }
            .onStart { refreshGetPostById(id) }
    }

    suspend fun refreshGetPostById(id: Int) {
        try {
            val dto = api.getPost(id.toString())
            postDao.insertPosts(listOf(dto.toEntity()))
        } catch (e: Exception) {

        }
    }

    override suspend fun createPost(post: Post): Result<Post> {
        return runCatching {
            api.createPost(post.toDto()).toDomain()
        }
    }


    override suspend fun updatePost(post: Post): Result<Post> {
        try {
            val dto = api.updatePost(post.id.toString(), post.toDto())
            return Result.success(dto.toDomain())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun deletePost(id: Int): Result<Unit> {
        try {
            api.deletePost(id.toString())
            postDao.deletePost(id)
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}