package com.dmb25.blogpostapp.data.mapper

import com.dmb25.blogpostapp.data.local.entity.CommentEntity
import com.dmb25.blogpostapp.data.remote.dto.CommentDto
import com.dmb25.blogpostapp.domain.model.Comment



fun CommentDto.toEntity() = CommentEntity(
    postId = postId,
    id = id,
    name = name,
    email = email,
    body = body
)

fun CommentDto.toDomain() = Comment(
    postId = postId,
    id = id,
    name = name,
    email = email,
    body = body
)

fun Comment.toEntity() = CommentEntity(
    postId = postId,
    id = id,
    name = name,
    email = email,
    body = body
)

fun CommentEntity.toDomain() = Comment(
    postId = postId,
    id = id,
    name = name,
    email = email,
    body = body
)