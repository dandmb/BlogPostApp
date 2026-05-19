package com.dmb25.blogpostapp.domain.usecase.users

import app.cash.turbine.test
import com.dmb25.blogpostapp.domain.model.User
import com.dmb25.blogpostapp.domain.repository.UserRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetUsersUseCaseTest {

    private lateinit var useCase: GetUsersUseCase
    private val userRepository: UserRepository = mockk()

    @Before
    fun setUp() {
        useCase = GetUsersUseCase(userRepository)
    }

    @Test
    fun `invoke should return users from repository`() = runTest {
        val testUsers = listOf(User(1, "Name", "Username", "Email", "Phone", "Website"))
        every { userRepository.getUsers() } returns flowOf(testUsers)

        useCase().test {
            assertThat(awaitItem()).isEqualTo(testUsers)
            awaitComplete()
        }
    }
}
