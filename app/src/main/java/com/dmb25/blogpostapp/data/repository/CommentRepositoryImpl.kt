package com.dmb25.blogpostapp.data.repository

import com.dmb25.blogpostapp.data.local.dao.CommentDao
import com.dmb25.blogpostapp.data.remote.BlogApiService
import com.dmb25.blogpostapp.domain.model.Comment
import com.dmb25.blogpostapp.domain.repository.CommentRepository
import kotlinx.coroutines.flow.Flow

class CommentRepositoryImpl(
    private val api: BlogApiService,
    private val commentDao: CommentDao
): CommentRepository {
    override fun getCommentsByPost(postId: Int): Flow<List<Comment>> {
        TODO("Not yet implemented")
    }
}