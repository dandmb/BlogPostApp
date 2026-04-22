package com.dmb25.blogpostapp.domain.usecase.posts

import com.dmb25.blogpostapp.domain.repository.PostRepository
import com.dmb25.blogpostapp.domain.repository.UserRepository

class GetPostsByUserUseCase(
    private val postRepository: PostRepository
) {
    operator fun invoke(userId: Int) = postRepository.getPostsByUser(userId)
}