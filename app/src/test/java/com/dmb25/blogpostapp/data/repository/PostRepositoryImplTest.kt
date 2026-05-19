package com.dmb25.blogpostapp.data.repository

import app.cash.turbine.test
import com.dmb25.blogpostapp.data.local.dao.PostDao
import com.dmb25.blogpostapp.data.local.entity.PostEntity
import com.dmb25.blogpostapp.data.remote.BlogApiService
import com.dmb25.blogpostapp.data.remote.dto.PostDto
import com.dmb25.blogpostapp.domain.model.Post
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class PostRepositoryImplTest {

    private lateinit var repository: PostRepositoryImpl
    private val api: BlogApiService = mockk()
    private val postDao: PostDao = mockk()

    private val testPostDto = PostDto(1, 1, "Title", "Body")
    private val testPostEntity = PostEntity(1, 1, "Title", "Body")
    private val testPost = Post(1, 1, "Title", "Body")

    @Before
    fun setUp() {
        repository = PostRepositoryImpl(api, postDao)
    }

    @Test
    fun `getPostsByUser should return data from DAO and trigger refresh from API`() = runTest {
        every { postDao.getPostsByUserId(1) } returns flowOf(listOf(testPostEntity))
        coEvery { api.getUserPosts("1") } returns listOf(testPostDto)
        coEvery { postDao.insertPosts(any()) } returns Unit

        repository.getPostsByUser(1).test {
            val result = awaitItem()
            assertThat(result).hasSize(1)
            assertThat(result[0].id).isEqualTo(testPostEntity.id)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify { api.getUserPosts("1") }
        coVerify { postDao.insertPosts(any()) }
    }

    @Test
    fun `createPost should call API and return success`() = runTest {
        coEvery { api.createPost(any()) } returns testPostDto

        val result = repository.createPost(testPost)

        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()?.id).isEqualTo(testPostDto.id)
        coVerify { api.createPost(any()) }
    }

    @Test
    fun `deletePost should call API and DAO`() = runTest {
        coEvery { api.deletePost("1") } returns Unit
        coEvery { postDao.deletePost(1) } returns Unit

        val result = repository.deletePost(1)

        assertThat(result.isSuccess).isTrue()
        coVerify { api.deletePost("1") }
        coVerify { postDao.deletePost(1) }
    }

    @Test
    fun `updatePost should call API and return success`() = runTest {
        coEvery { api.updatePost("1", any()) } returns testPostDto

        val result = repository.updatePost(testPost)

        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()?.id).isEqualTo(testPostDto.id)
        coVerify { api.updatePost("1", any()) }
    }
}
