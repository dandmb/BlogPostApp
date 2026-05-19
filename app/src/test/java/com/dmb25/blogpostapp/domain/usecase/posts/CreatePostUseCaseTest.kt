package com.dmb25.blogpostapp.domain.usecase.posts

import com.dmb25.blogpostapp.domain.model.Post
import com.dmb25.blogpostapp.domain.repository.PostRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CreatePostUseCaseTest {

    private val repository: PostRepository = mockk()
    private val useCase = CreatePostUseCase(repository)

    @Test
    fun `invoke should call repository createPost`() = runTest {
        val testPost = Post(1, 1, "Title", "Body")
        coEvery { repository.createPost(testPost) } returns Result.success(testPost)

        val result = useCase(testPost)

        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(testPost)
    }
}
