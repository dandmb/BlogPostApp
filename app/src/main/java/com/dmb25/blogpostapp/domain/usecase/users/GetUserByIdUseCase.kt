package com.dmb25.blogpostapp.domain.usecase.users

import com.dmb25.blogpostapp.domain.repository.UserRepository

class GetUserByIdUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(id: Int) = userRepository.getUserById(id)
}