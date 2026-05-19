package com.dmb25.blogpostapp.domain.usecase.posts

import app.cash.turbine.test
import com.dmb25.blogpostapp.domain.model.Post
import com.dmb25.blogpostapp.domain.model.User
import com.dmb25.blogpostapp.domain.repository.PostRepository
import com.dmb25.blogpostapp.domain.repository.UserRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetUserWithPostsUseCaseTest {

    private lateinit var useCase: GetUserWithPostsUseCase
    private val userRepository: UserRepository = mockk()
    private val postRepository: PostRepository = mockk()

    private val testUser = User(1, "Name", "Username", "Email", "Phone", "Website")
    private val testPosts = listOf(Post(1, 1, "Title", "Body"))

    @Before
    fun setUp() {
        useCase = GetUserWithPostsUseCase(userRepository, postRepository)
    }

    @Test
    fun `invoke should combine user and posts from repositories`() = runTest {
        every { userRepository.getUserById(1) } returns flowOf(testUser)
        every { postRepository.getPostsByUser(1) } returns flowOf(testPosts)

        useCase(1).test {
            val result = awaitItem()
            assertThat(result.first).isEqualTo(testUser)
            assertThat(result.second).isEqualTo(testPosts)
            awaitComplete()
        }
    }
}
