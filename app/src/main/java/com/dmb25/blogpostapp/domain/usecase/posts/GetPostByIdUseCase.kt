package com.dmb25.blogpostapp.domain.usecase.posts

import com.dmb25.blogpostapp.domain.repository.PostRepository

class GetPostByIdUseCase(
    private val postRepository: PostRepository
) {
    operator fun invoke(id: Int) = postRepository.getPostById(id)
}