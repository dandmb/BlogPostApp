package com.dmb25.blogpostapp.domain.repository

import com.dmb25.blogpostapp.domain.model.Comment
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    fun getCommentsByPost(postId: Int): Flow<List<Comment>>
}