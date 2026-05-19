package com.dmb25.blogpostapp.domain.usecase.posts

import com.dmb25.blogpostapp.domain.repository.PostRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DeletePostUseCaseTest {

    private val repository: PostRepository = mockk()
    private val useCase = DeletePostUseCase(repository)

    @Test
    fun `invoke should call repository deletePost`() = runTest {
        coEvery { repository.deletePost(1) } returns Result.success(Unit)

        val result = useCase(1)

        assertThat(result.isSuccess).isTrue()
    }
}
