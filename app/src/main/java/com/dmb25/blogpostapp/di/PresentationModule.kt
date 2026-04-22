package com.dmb25.blogpostapp.di

import com.dmb25.blogpostapp.presentation.detail.PostDetailViewModel
import com.dmb25.blogpostapp.presentation.posts.PostsViewModel
import com.dmb25.blogpostapp.presentation.users.UsersViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { PostsViewModel(get(), get(), get(), get()) }
    viewModel {
        PostDetailViewModel(get(), get(), get())
    }
    viewModel { UsersViewModel(get()) }
}