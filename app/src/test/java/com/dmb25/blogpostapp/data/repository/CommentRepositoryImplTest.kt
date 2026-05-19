package com.dmb25.blogpostapp.data.repository

import app.cash.turbine.test
import com.dmb25.blogpostapp.data.local.dao.CommentDao
import com.dmb25.blogpostapp.data.local.entity.CommentEntity
import com.dmb25.blogpostapp.data.remote.BlogApiService
import com.dmb25.blogpostapp.data.remote.dto.CommentDto
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CommentRepositoryImplTest {

    private lateinit var repository: CommentRepositoryImpl
    private val api: BlogApiService = mockk()
    private val commentDao: CommentDao = mockk()

    private val testCommentDto = CommentDto(1, 1, "Name", "Email", "Body")
    private val testCommentEntity = CommentEntity(1, 1, "Name", "Email", "Body")

    @Before
    fun setUp() {
        repository = CommentRepositoryImpl(api, commentDao)
    }

    @Test
    fun `getCommentsByPost should return data from DAO and trigger refresh from API`() = runTest {
        every { commentDao.getCommentsByPostId(1) } returns flowOf(listOf(testCommentEntity))
        coEvery { api.getPostComments("1") } returns listOf(testCommentDto)
        coEvery { commentDao.insertComments(any()) } returns Unit

        repository.getCommentsByPost(1).test {
            val result = awaitItem()
            assertThat(result).hasSize(1)
            assertThat(result[0].id).isEqualTo(testCommentEntity.id)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify { api.getPostComments("1") }
        coVerify { commentDao.insertComments(any()) }
    }
}
