package com.dmb25.blogpostapp.domain.repository

import com.dmb25.blogpostapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers() : Flow<List<User>>
    fun getUserById(id: Int): Flow<User?>
}