package com.dmb25.blogpostapp.data.mapper

import com.dmb25.blogpostapp.data.remote.dto.UserDto
import com.dmb25.blogpostapp.domain.model.User
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UserMapperTest {

    @Test
    fun `UserDto toDomain should map correctly`() {
        val dto = UserDto(
            id = 1,
            name = "John Doe",
            username = "johnd",
            email = "john@example.com",
            phone = "123-456",
            website = "example.com"
        )

        val domain = dto.toDomain()

        assertThat(domain.id).isEqualTo(dto.id)
        assertThat(domain.name).isEqualTo(dto.name)
        assertThat(domain.email).isEqualTo(dto.email)
        assertThat(domain.username).isEqualTo(dto.username)
        assertThat(domain.phone).isEqualTo(dto.phone)
        assertThat(domain.website).isEqualTo(dto.website)
    }

    @Test
    fun `User toEntity should map correctly`() {
        val user = User(
            id = 1,
            name = "John Doe",
            username = "johnd",
            email = "john@example.com",
            phone = "123-456",
            website = "example.com"
        )

        val entity = user.toEntity()

        assertThat(entity.id).isEqualTo(user.id)
        assertThat(entity.name).isEqualTo(user.name)
        assertThat(entity.email).isEqualTo(user.email)
        assertThat(entity.username).isEqualTo(user.username)
        assertThat(entity.phone).isEqualTo(user.phone)
        assertThat(entity.website).isEqualTo(user.website)
    }
}
