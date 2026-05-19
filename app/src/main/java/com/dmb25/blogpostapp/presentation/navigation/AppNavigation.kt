package com.dmb25.blogpostapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dmb25.blogpostapp.presentation.detail.PostDetailScreen
import com.dmb25.blogpostapp.presentation.posts.PostsScreen
import com.dmb25.blogpostapp.presentation.users.UsersScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Users.route
    ) {
        composable(Screen.Users.route) {
            UsersScreen(
                onUserClick = { userId ->
                    navController.navigate(Screen.Posts.createRoute(userId))
                }
            )
        }

        composable(
            route = Screen.Posts.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType }
            )
        ) {
            PostsScreen(
                onPostClick = { postId ->
                    navController.navigate(Screen.PostDetail.createRoute(postId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.PostDetail.route,
            arguments = listOf(
                navArgument("postId") { type = NavType.IntType }
            )
        ) {
            PostDetailScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}