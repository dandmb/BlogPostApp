package com.dmb25.blogpostapp.domain.repository

import com.dmb25.blogpostapp.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPostsByUser(userId: Int): Flow<List<Post>>
    fun getPostById(id: Int): Flow<Post?>
    suspend fun createPost(post: Post): Result<Post>
    suspend fun updatePost(post: Post): Result<Post>
    suspend fun deletePost(id: Int): Result<Unit>
}