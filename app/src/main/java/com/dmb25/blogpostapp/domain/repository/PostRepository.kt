package com.dmb25.blogpostapp.domain.repository

import com.dmb25.blogpostapp.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPostsByUser(userId: Int): Flow<List<Post>>
    fun getPostById(id: Int): Flow<Post?>
    fun createPost(post: Post): Result<Post>
    fun updatePost(post: Post): Result<Post>
    fun deletePost(id: Int): Result<Unit>
}