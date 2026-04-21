package com.dmb25.blogpostapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey val id: Int,

    @ColumnInfo(name = "post_id")
    val postId: Int,
    val name: String,
    val email: String,
    val body: String,
)
