package com.dmb25.blogpostapp.domain.usecase.posts

import com.dmb25.blogpostapp.domain.repository.PostRepository

class DeletePostUseCase(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(id: Int) = postRepository.deletePost(id)
}