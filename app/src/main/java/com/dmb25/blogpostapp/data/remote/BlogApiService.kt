package com.dmb25.blogpostapp.data.remote

import com.dmb25.blogpostapp.data.remote.dto.CommentDto
import com.dmb25.blogpostapp.data.remote.dto.PostDto
import com.dmb25.blogpostapp.data.remote.dto.UserDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class BlogApiService(
    private val client: HttpClient
) {
    suspend fun getUsers(): List<UserDto> =
        client.get("users").body()

    suspend fun getUserById(id: String): UserDto =
        client.get("users/$id").body()

    suspend fun getPost(id: String): PostDto =
        client.get("posts/$id").body()

    suspend fun getUserPosts(id: String): List<PostDto> =
        client.get("posts") {
            parameter("userId", id)
        }.body()

    suspend fun getPostComments(id: String): List<CommentDto> =
        client.get("posts/$id/comments").body()

    suspend fun createPost(postDto: PostDto): PostDto =
        client.post("posts") {
            contentType(ContentType.Application.Json)
            setBody(postDto)
        }.body()

    suspend fun deletePost(id: String) {
        client.delete("posts/$id")
    }

    suspend fun updatePost(id: String, postDto: PostDto): PostDto =
        client.put("posts/$id") {
            contentType(ContentType.Application.Json)
            setBody(postDto)
        }.body()
}