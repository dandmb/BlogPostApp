package com.dmb25.blogpostapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.dmb25.blogpostapp.domain.model.Comment
import com.dmb25.blogpostapp.domain.model.Post
import com.dmb25.blogpostapp.domain.usecase.posts.GetPostWithCommentsUseCase
import com.dmb25.blogpostapp.domain.usecase.posts.UpdatePostUseCase
import com.dmb25.blogpostapp.presentation.events.PostEvent
import com.dmb25.blogpostapp.presentation.ui.UiState
import com.dmb25.blogpostapp.util.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: PostDetailViewModel
    private val updatePostUseCase: UpdatePostUseCase = mockk()
    private val getPostWithCommentsUseCase: GetPostWithCommentsUseCase = mockk()
    private val savedStateHandle: SavedStateHandle = SavedStateHandle(mapOf("postId" to 1))

    private val testPost = Post(1, 1, "Title", "Body")
    private val testComments = listOf(Comment(1, 1, "Name", "Email", "Body"))

    @Before
    fun setUp() {
        every { getPostWithCommentsUseCase(1) } returns flowOf(testPost to testComments)
    }

    @Test
    fun `init should load post details`() = runTest {
        viewModel = PostDetailViewModel(
            savedStateHandle,
            updatePostUseCase,
            getPostWithCommentsUseCase
        )

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state).isInstanceOf(UiState.Success::class.java)
            val data = (state as UiState.Success).data
            assertThat(data.first).isEqualTo(testPost)
            assertThat(data.second).isEqualTo(testComments)
        }
    }

    @Test
    fun `updatePost should emit PostUpdated event on success`() = runTest {
        viewModel = PostDetailViewModel(
            savedStateHandle,
            updatePostUseCase,
            getPostWithCommentsUseCase
        )

        coEvery { updatePostUseCase(testPost) } returns Result.success(testPost)

        viewModel.event.test {
            viewModel.updatePost(testPost)
            val event = awaitItem()
            assertThat(event).isInstanceOf(PostEvent.PostUpdated::class.java)
        }
    }

    @Test
    fun `updatePost should emit Error event on failure`() = runTest {
        viewModel = PostDetailViewModel(
            savedStateHandle,
            updatePostUseCase,
            getPostWithCommentsUseCase
        )

        val errorMessage = "Update failed"
        coEvery { updatePostUseCase(testPost) } returns Result.failure(Exception(errorMessage))

        viewModel.event.test {
            viewModel.updatePost(testPost)
            val event = awaitItem()
            assertThat(event).isInstanceOf(PostEvent.Error::class.java)
            assertThat((event as PostEvent.Error).message).isEqualTo(errorMessage)
        }
    }
}
