package com.dmb25.blogpostapp.domain.usecase.users

import com.dmb25.blogpostapp.domain.repository.UserRepository

class GetUsersUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke() = userRepository.getUsers()
}