package com.dmb25.blogpostapp.di

import com.dmb25.blogpostapp.domain.usecase.comments.GetCommentsByPostUseCase
import com.dmb25.blogpostapp.domain.usecase.posts.CreatePostUseCase
import com.dmb25.blogpostapp.domain.usecase.posts.DeletePostUseCase
import com.dmb25.blogpostapp.domain.usecase.posts.GetPostByIdUseCase
import com.dmb25.blogpostapp.domain.usecase.posts.GetPostWithCommentsUseCase
import com.dmb25.blogpostapp.domain.usecase.posts.GetPostsByUserUseCase
import com.dmb25.blogpostapp.domain.usecase.posts.GetUserWithPostsUseCase
import com.dmb25.blogpostapp.domain.usecase.posts.UpdatePostUseCase
import com.dmb25.blogpostapp.domain.usecase.users.GetUserByIdUseCase
import com.dmb25.blogpostapp.domain.usecase.users.GetUsersUseCase
import org.koin.dsl.module

val domainModule = module{
    factory { GetUsersUseCase(get()) }
    factory { GetUserByIdUseCase(get()) }
    factory { GetCommentsByPostUseCase(get()) }
    factory { CreatePostUseCase(get()) }
    factory { DeletePostUseCase(get()) }
    factory { GetPostByIdUseCase(get()) }
    factory { GetPostsByUserUseCase(get()) }
    factory { GetPostWithCommentsUseCase(get(), get()) }
    factory { GetUserWithPostsUseCase(get(), get()) }
    factory { UpdatePostUseCase(get()) }
}