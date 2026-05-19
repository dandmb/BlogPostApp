package com.dmb25.blogpostapp.presentation.posts

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.dmb25.blogpostapp.domain.model.Post
import com.dmb25.blogpostapp.domain.model.User
import com.dmb25.blogpostapp.domain.usecase.posts.CreatePostUseCase
import com.dmb25.blogpostapp.domain.usecase.posts.DeletePostUseCase
import com.dmb25.blogpostapp.domain.usecase.posts.GetUserWithPostsUseCase
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

class PostsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: PostsViewModel
    private val createPostUseCase: CreatePostUseCase = mockk()
    private val deletePostUseCase: DeletePostUseCase = mockk()
    private val getUserWithPostsUseCase: GetUserWithPostsUseCase = mockk()
    private val savedStateHandle: SavedStateHandle = SavedStateHandle(mapOf("userId" to 1))

    private val testUser = User(1, "Name", "Username", "Email", "Phone", "Website")
    private val testPosts = listOf(
        Post(1, 1, "Title 1", "Body 1"),
        Post(2, 1, "Another Title", "Body 2")
    )

    @Before
    fun setUp() {
        every { getUserWithPostsUseCase(1) } returns flowOf(testUser to testPosts)
    }

    @Test
    fun `init should load user with posts`() = runTest {
        viewModel = PostsViewModel(
            savedStateHandle,
            createPostUseCase,
            deletePostUseCase,
            getUserWithPostsUseCase
        )

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state).isInstanceOf(UiState.Success::class.java)
            val successData = (state as UiState.Success).data
            assertThat(successData.first).isEqualTo(testUser)
            assertThat(successData.second).isEqualTo(testPosts)
        }
    }

    @Test
    fun `search query change should filter posts`() = runTest {
        viewModel = PostsViewModel(
            savedStateHandle,
            createPostUseCase,
            deletePostUseCase,
            getUserWithPostsUseCase
        )

        viewModel.onSearchQueryChange("Another")

        viewModel.filteredPosts.test {
            // Skip initial state
            skipItems(1) 
            val state = awaitItem()
            assertThat(state).isInstanceOf(UiState.Success::class.java)
            val filtered = (state as UiState.Success).data.second
            assertThat(filtered).hasSize(1)
            assertThat(filtered[0].title).contains("Another")
        }
    }

    @Test
    fun `createPost should emit PostCreated event on success`() = runTest {
        viewModel = PostsViewModel(
            savedStateHandle,
            createPostUseCase,
            deletePostUseCase,
            getUserWithPostsUseCase
        )

        val newPost = Post(0, 1, "New", "Body")
        coEvery { createPostUseCase(newPost) } returns Result.success(newPost)

        viewModel.events.test {
            viewModel.createPost(newPost)
            assertThat(awaitItem()).isEqualTo(PostEvent.PostCreated)
        }
    }

    @Test
    fun `deletePost should emit PostDeleted event on success`() = runTest {
        viewModel = PostsViewModel(
            savedStateHandle,
            createPostUseCase,
            deletePostUseCase,
            getUserWithPostsUseCase
        )

        coEvery { deletePostUseCase(1) } returns Result.success(Unit)

        viewModel.events.test {
            viewModel.deletePost(1)
            assertThat(awaitItem()).isEqualTo(PostEvent.PostDeleted)
        }
    }
}
