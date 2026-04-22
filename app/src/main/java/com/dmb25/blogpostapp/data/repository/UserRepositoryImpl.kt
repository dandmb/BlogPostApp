package com.dmb25.blogpostapp.data.repository

import com.dmb25.blogpostapp.data.local.dao.UserDao
import com.dmb25.blogpostapp.data.mapper.toDomain
import com.dmb25.blogpostapp.data.mapper.toEntity
import com.dmb25.blogpostapp.data.remote.BlogApiService
import com.dmb25.blogpostapp.domain.model.User
import com.dmb25.blogpostapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class UserRepositoryImpl(
    private val api: BlogApiService,
    private val userDao: UserDao
) : UserRepository {
    override fun getUsers(): Flow<List<User>> {
        return userDao.getUsers()
            .map { entities -> entities.map { it.toDomain() } }
            .onStart {
                refreshUsers()
            }
    }

    private suspend fun refreshUsers() {
        try {
            val dtos = api.getUsers()
            userDao.insertUsers(dtos.map { it.toEntity() })
        } catch (e: Exception) {
        }
    }
    override fun getUserById(id: Int): Flow<User?> {
        return userDao.getUserById(id)
            .map { it?.toDomain() }
            .onStart { refreshUserById(id) }
    }

    private suspend fun refreshUserById(id: Int) {
        try {
            val dto = api.getUserById(id.toString())
            userDao.insertUsers(listOf(dto.toEntity()))
        } catch (e: Exception) { }
    }
}