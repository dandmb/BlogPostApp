package com.dmb25.blogpostapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dmb25.blogpostapp.data.local.dao.CommentDao
import com.dmb25.blogpostapp.data.local.dao.PostDao
import com.dmb25.blogpostapp.data.local.dao.UserDao
import com.dmb25.blogpostapp.data.local.entity.CommentEntity
import com.dmb25.blogpostapp.data.local.entity.PostEntity
import com.dmb25.blogpostapp.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, PostEntity::class, CommentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BlogDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao

    companion object {
        fun create(context: Context): BlogDatabase {
            return Room.databaseBuilder(
                context,
                BlogDatabase::class.java,
                "blog_database"
            ).build()
        }
    }
}