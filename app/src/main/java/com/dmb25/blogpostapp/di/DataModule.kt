package com.dmb25.blogpostapp.di

import com.dmb25.blogpostapp.data.local.BlogDatabase
import com.dmb25.blogpostapp.data.remote.BlogApiService
import com.dmb25.blogpostapp.data.repository.CommentRepositoryImpl
import com.dmb25.blogpostapp.data.repository.PostRepositoryImpl
import com.dmb25.blogpostapp.data.repository.UserRepositoryImpl
import com.dmb25.blogpostapp.domain.repository.CommentRepository
import com.dmb25.blogpostapp.domain.repository.PostRepository
import com.dmb25.blogpostapp.domain.repository.UserRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            install(Logging) { level = LogLevel.INFO }
            install(HttpTimeout) { requestTimeoutMillis = 15_000 }
            install(DefaultRequest) {
                url("https://jsonplaceholder.typicode.com/")
            }
            expectSuccess = true
        }
    }
    single { BlogApiService(get()) }

    single { BlogDatabase.create(androidContext()) }
    single { get<BlogDatabase>().userDao() }
    single { get<BlogDatabase>().postDao() }
    single { get<BlogDatabase>().commentDao()}

    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    single<PostRepository> { PostRepositoryImpl(get(), get()) }
    single<CommentRepository> { CommentRepositoryImpl(get(), get()) }

}