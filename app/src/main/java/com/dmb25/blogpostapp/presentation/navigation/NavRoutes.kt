package com.dmb25.blogpostapp.presentation.navigation

sealed class Screen(val route: String) {
    object Users : Screen("users")

    object Posts : Screen("posts/{userId}") {
        fun createRoute(userId: Int) = "posts/$userId"
    }

    object PostDetail : Screen("post_detail/{postId}") {
        fun createRoute(postId: Int) = "post_detail/$postId"
    }
}