package com.dmb25.blogpostapp.presentation.users

import app.cash.turbine.test
import com.dmb25.blogpostapp.domain.model.User
import com.dmb25.blogpostapp.domain.usecase.users.GetUsersUseCase
import com.dmb25.blogpostapp.presentation.ui.UiState
import com.dmb25.blogpostapp.util.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class UsersViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getUsersUseCase: GetUsersUseCase = mockk()
    private lateinit var viewModel: UsersViewModel

    @Test
    fun `loadUsers should update uiState to Success`() = runTest {
        val testUsers = listOf(User(1, "Name", "Username", "Email", "Phone", "Website"))
        every { getUsersUseCase() } returns flowOf(testUsers)
        
        viewModel = UsersViewModel(getUsersUseCase)

        viewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(UiState.Idle)
            viewModel.loadUsers()

            val next = awaitItem()
            if (next is UiState.Loading) {
                val successState = awaitItem()
                assertThat(successState).isInstanceOf(UiState.Success::class.java)
                assertThat((successState as UiState.Success).data).isEqualTo(testUsers)
            } else {
                assertThat(next).isInstanceOf(UiState.Success::class.java)
                assertThat((next as UiState.Success).data).isEqualTo(testUsers)
            }
        }
    }

    @Test
    fun `loadUsers should update uiState to Error on failure`() = runTest {
        val errorMessage = "Network Error"
        every { getUsersUseCase() } returns kotlinx.coroutines.flow.flow { throw Exception(errorMessage) }
        
        viewModel = UsersViewModel(getUsersUseCase)

        viewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(UiState.Idle)
            viewModel.loadUsers()
            val next = awaitItem()
            if (next is UiState.Loading) {
                val errorState = awaitItem()
                assertThat(errorState).isInstanceOf(UiState.Error::class.java)
                assertThat((errorState as UiState.Error).message).isEqualTo(errorMessage)
            } else {
                assertThat(next).isInstanceOf(UiState.Error::class.java)
                assertThat((next as UiState.Error).message).isEqualTo(errorMessage)
            }
        }
    }
}
