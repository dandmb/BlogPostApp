package com.dmb25.blogpostapp.data.mapper

import com.dmb25.blogpostapp.data.local.entity.UserEntity
import com.dmb25.blogpostapp.data.remote.dto.UserDto
import com.dmb25.blogpostapp.domain.model.User


fun UserDto.toDomain() = User(
    id = id,
    name = name,
    email = email,
    username = username,
    phone = phone,
    website = website,
)

fun User.toEntity() = UserEntity(
    id = id,
    name = name,
    email = email,
    username = username,
    phone = phone,
    website = website,
)

fun UserEntity.toDomain() = User(
    id = id,
    name = name,
    email = email,
    username = username,
    phone = phone,
    website = website
)

fun UserDto.toEntity() = UserEntity(
    id = id,
    name = name,
    email = email,
    username = username,
    phone = phone,
    website = website
)