package com.dmb25.blogpostapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.util.TableInfo

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    val title: String,
    val body: String
)
