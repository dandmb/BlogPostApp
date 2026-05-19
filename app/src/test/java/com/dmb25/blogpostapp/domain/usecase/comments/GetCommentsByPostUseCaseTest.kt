package com.dmb25.blogpostapp.domain.usecase.comments

import app.cash.turbine.test
import com.dmb25.blogpostapp.domain.model.Comment
import com.dmb25.blogpostapp.domain.repository.CommentRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetCommentsByPostUseCaseTest {

    private lateinit var useCase: GetCommentsByPostUseCase
    private val commentRepository: CommentRepository = mockk()

    private val testComments = listOf(
        Comment(1, 1, "Name 1", "email1@test.com", "Body 1"),
        Comment(1, 2, "Name 2", "email2@test.com", "Body 2")
    )

    @Before
    fun setUp() {
        useCase = GetCommentsByPostUseCase(commentRepository)
    }

    @Test
    fun `invoke should return comments from repository`() = runTest {
        every { commentRepository.getCommentsByPost(1) } returns flowOf(testComments)

        useCase(1).test {
            val result = awaitItem()
            assertThat(result).isEqualTo(testComments)
            awaitComplete()
        }
    }
}
