package com.dmb25.blogpostapp.data.repository

import com.dmb25.blogpostapp.data.local.dao.CommentDao
import com.dmb25.blogpostapp.data.mapper.toDomain
import com.dmb25.blogpostapp.data.mapper.toEntity
import com.dmb25.blogpostapp.data.remote.BlogApiService
import com.dmb25.blogpostapp.domain.model.Comment
import com.dmb25.blogpostapp.domain.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class CommentRepositoryImpl(
    private val api: BlogApiService,
    private val commentDao: CommentDao
): CommentRepository {
    override fun getCommentsByPost(postId: Int): Flow<List<Comment>> {
        return commentDao.getCommentsByPostId(postId)
            .map { entities -> entities.map { it.toDomain() } }
            .onStart { refreshGetCommentsByPost(postId) }
    }
    private suspend fun refreshGetCommentsByPost(postId: Int) {
        try {
            val dtos = api.getPostComments(postId.toString())
            commentDao.insertComments(dtos.map { it.toEntity() })
        }catch (e: Exception){

        }
    }
}