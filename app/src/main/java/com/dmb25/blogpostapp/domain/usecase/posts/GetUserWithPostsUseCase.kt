package com.dmb25.blogpostapp.domain.usecase.posts

import com.dmb25.blogpostapp.domain.model.Post
import com.dmb25.blogpostapp.domain.model.User
import com.dmb25.blogpostapp.domain.repository.PostRepository
import com.dmb25.blogpostapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetUserWithPostsUseCase(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) {
    operator fun invoke(userId: Int): Flow<Pair<User?, List<Post>>> {
        val user = userRepository.getUserById(userId)
        val posts = postRepository.getPostsByUser(userId)
        return combine(user, posts) { user, posts ->
            Pair(user, posts)
        }
    }
}