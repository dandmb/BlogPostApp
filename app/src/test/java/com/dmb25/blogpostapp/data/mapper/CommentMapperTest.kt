package com.dmb25.blogpostapp.data.mapper

import com.dmb25.blogpostapp.data.remote.dto.CommentDto
import com.dmb25.blogpostapp.domain.model.Comment
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CommentMapperTest {

    @Test
    fun `CommentDto toDomain should map correctly`() {
        val dto = CommentDto(
            postId = 1,
            id = 1,
            name = "name",
            email = "email@test.com",
            body = "body content"
        )

        val domain = dto.toDomain()

        assertThat(domain.postId).isEqualTo(dto.postId)
        assertThat(domain.id).isEqualTo(dto.id)
        assertThat(domain.name).isEqualTo(dto.name)
        assertThat(domain.email).isEqualTo(dto.email)
        assertThat(domain.body).isEqualTo(dto.body)
    }

    @Test
    fun `Comment toEntity should map correctly`() {
        val comment = Comment(
            postId = 1,
            id = 1,
            name = "name",
            email = "email@test.com",
            body = "body content"
        )

        val entity = comment.toEntity()

        assertThat(entity.postId).isEqualTo(comment.postId)
        assertThat(entity.id).isEqualTo(comment.id)
        assertThat(entity.name).isEqualTo(comment.name)
        assertThat(entity.email).isEqualTo(comment.email)
        assertThat(entity.body).isEqualTo(comment.body)
    }
}
