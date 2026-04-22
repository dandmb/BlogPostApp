package com.dmb25.blogpostapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity (
    @PrimaryKey val id: Int,
    val name: String,
    @ColumnInfo(name = "user_name")
    val username: String,
    @ColumnInfo(name = "email_address")
    val email: String,
    val phone: String,
    val website: String
)