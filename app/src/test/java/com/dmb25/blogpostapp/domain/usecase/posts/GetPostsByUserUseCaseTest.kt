package com.dmb25.blogpostapp.domain.usecase.posts

import app.cash.turbine.test
import com.dmb25.blogpostapp.domain.model.Post
import com.dmb25.blogpostapp.domain.repository.PostRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetPostsByUserUseCaseTest {

    private val repository: PostRepository = mockk()
    private val useCase = GetPostsByUserUseCase(repository)

    @Test
    fun `invoke should return posts from repository`() = runTest {
        val testPosts = listOf(Post(1, 1, "Title", "Body"))
        every { repository.getPostsByUser(1) } returns flowOf(testPosts)

        useCase(1).test {
            assertThat(awaitItem()).isEqualTo(testPosts)
            awaitComplete()
        }
    }
}
