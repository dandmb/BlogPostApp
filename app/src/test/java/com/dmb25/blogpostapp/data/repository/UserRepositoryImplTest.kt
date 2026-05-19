package com.dmb25.blogpostapp.data.repository

import app.cash.turbine.test
import com.dmb25.blogpostapp.data.local.dao.UserDao
import com.dmb25.blogpostapp.data.local.entity.UserEntity
import com.dmb25.blogpostapp.data.remote.BlogApiService
import com.dmb25.blogpostapp.data.remote.dto.UserDto
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {

    private lateinit var repository: UserRepositoryImpl
    private val api: BlogApiService = mockk()
    private val userDao: UserDao = mockk()

    private val testUserDto = UserDto(1, "Name", "Username", "Email", "Phone", "Website")
    private val testUserEntity = UserEntity(1, "Name", "Username", "Email", "Phone", "Website")

    @Before
    fun setUp() {
        repository = UserRepositoryImpl(api, userDao)
    }

    @Test
    fun `getUsers should return data from DAO and trigger refresh from API`() = runTest {
        every { userDao.getUsers() } returns flowOf(listOf(testUserEntity))
        coEvery { api.getUsers() } returns listOf(testUserDto)
        coEvery { userDao.insertUsers(any()) } returns Unit

        repository.getUsers().test {
            val result = awaitItem()
            assertThat(result).hasSize(1)
            assertThat(result[0].id).isEqualTo(testUserEntity.id)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify { api.getUsers() }
        coVerify { userDao.insertUsers(any()) }
    }

    @Test
    fun `getUserById should return user from DAO and trigger refresh from API`() = runTest {
        every { userDao.getUserById(1) } returns flowOf(testUserEntity)
        coEvery { api.getUserById("1") } returns testUserDto
        coEvery { userDao.insertUsers(any()) } returns Unit

        repository.getUserById(1).test {
            val result = awaitItem()
            assertThat(result?.id).isEqualTo(testUserEntity.id)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify { api.getUserById("1") }
        coVerify { userDao.insertUsers(any()) }
    }
}
