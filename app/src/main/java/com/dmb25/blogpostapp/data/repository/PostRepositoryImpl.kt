package com.dmb25.blogpostapp.data.repository

import com.dmb25.blogpostapp.data.local.dao.PostDao
import com.dmb25.blogpostapp.data.remote.BlogApiService
import com.dmb25.blogpostapp.domain.model.Post
import com.dmb25.blogpostapp.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class PostRepositoryImpl(
    private val api: BlogApiService,
    private val postDao: PostDao
) : PostRepository {
    override fun getPostsByUser(userId: Int): Flow<List<Post>> {
        TODO("Not yet implemented")
    }

    override fun getPostById(id: Int): Flow<Post?> {
        TODO("Not yet implemented")
    }

    override fun createPost(post: Post): Result<Post> {
        TODO("Not yet implemented")
    }

    override fun updatePost(post: Post): Result<Post> {
        TODO("Not yet implemented")
    }

    override fun deletePost(id: Int): Result<Unit> {
        TODO("Not yet implemented")
    }
}