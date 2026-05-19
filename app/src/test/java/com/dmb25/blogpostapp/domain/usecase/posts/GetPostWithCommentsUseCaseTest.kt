package com.dmb25.blogpostapp.domain.usecase.posts

import app.cash.turbine.test
import com.dmb25.blogpostapp.domain.model.Comment
import com.dmb25.blogpostapp.domain.model.Post
import com.dmb25.blogpostapp.domain.repository.CommentRepository
import com.dmb25.blogpostapp.domain.repository.PostRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetPostWithCommentsUseCaseTest {

    private lateinit var useCase: GetPostWithCommentsUseCase
    private val postRepository: PostRepository = mockk()
    private val commentRepository: CommentRepository = mockk()

    private val testPost = Post(1, 1, "Title", "Body")
    private val testComments = listOf(Comment(1, 1, "Name", "Email", "Body"))

    @Before
    fun setUp() {
        useCase = GetPostWithCommentsUseCase(postRepository, commentRepository)
    }

    @Test
    fun `invoke should combine post and comments`() = runTest {
        every { postRepository.getPostById(1) } returns flowOf(testPost)
        every { commentRepository.getCommentsByPost(1) } returns flowOf(testComments)

        useCase(1).test {
            val result = awaitItem()
            assertThat(result.first).isEqualTo(testPost)
            assertThat(result.second).isEqualTo(testComments)
            awaitComplete()
        }
    }
}
