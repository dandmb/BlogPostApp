package com.dmb25.blogpostapp.domain.usecase.posts

import com.dmb25.blogpostapp.domain.model.Comment
import com.dmb25.blogpostapp.domain.model.Post
import com.dmb25.blogpostapp.domain.repository.CommentRepository
import com.dmb25.blogpostapp.domain.repository.PostRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map


@OptIn(ExperimentalCoroutinesApi::class)
class GetPostWithCommentsUseCase(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository
) {
    operator fun invoke(postId: Int): Flow<Pair<Post?, List<Comment>>> {
        return postRepository.getPostById(postId)
            .flatMapLatest { post ->
                commentRepository.getCommentsByPost(postId)
                    .map { comments -> Pair(post, comments) }
            }
    }
}