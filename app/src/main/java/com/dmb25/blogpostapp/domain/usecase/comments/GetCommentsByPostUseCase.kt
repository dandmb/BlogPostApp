package com.dmb25.blogpostapp.domain.usecase.comments

import com.dmb25.blogpostapp.domain.repository.CommentRepository

class GetCommentsByPostUseCase(
    private val commentRepository: CommentRepository
) {
    operator fun invoke(postId: Int) = commentRepository.getCommentsByPost(postId)
}