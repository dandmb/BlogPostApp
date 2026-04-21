package com.dmb25.blogpostapp.data.mapper

import com.dmb25.blogpostapp.data.local.entity.PostEntity
import com.dmb25.blogpostapp.data.remote.dto.PostDto
import com.dmb25.blogpostapp.domain.model.Post


fun PostDto.toDomain() = Post(
    userId = userId,
    id = id,
    title = title,
    body = body
)


fun Post.toEntity() = PostEntity(
    userId = userId,
    id = id,
    title = title,
    body = body
)

fun PostEntity.toDomain() = Post(
    userId = userId,
    id = id,
    title = title,
    body = body
)