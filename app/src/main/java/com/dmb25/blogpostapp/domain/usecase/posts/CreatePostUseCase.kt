package com.dmb25.blogpostapp.domain.usecase.posts

import com.dmb25.blogpostapp.domain.model.Post
import com.dmb25.blogpostapp.domain.repository.PostRepository

class CreatePostUseCase(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(post: Post) = postRepository.createPost(post)
}