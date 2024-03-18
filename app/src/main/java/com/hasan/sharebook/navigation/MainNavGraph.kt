package com.hasan.sharebook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.hasan.sharebook.screens.Add
import com.hasan.sharebook.screens.BookInfo
import com.hasan.sharebook.screens.Chats
import com.hasan.sharebook.screens.Home
import com.hasan.sharebook.screens.Info
import com.hasan.sharebook.screens.Profile
import com.hasan.sharebook.screens.Requests

@Composable
fun MainNavGraph(navController: NavHostController){
    NavHost(navController = navController, route = Screen.Main.route, startDestination = Screen.HomeScreen.route){

        composable(route = Screen.HomeScreen.route) {
            Home(navController)
        }
        composable(route = Screen.ChatScreen.route) {
            Chats()
        }
        composable(route = Screen.AddScreen.route) {
            Add()
        }
        composable(route = Screen.ProfileScreen.route) {
            Profile(navController)
        }
        composable(route = Screen.Info.route) {
            Info()
        }
        composable(route = Screen.Requests.route) {
            Requests()
        }
        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Screen.Details.route,
        startDestination = DetailsScreen.BookInfo.route
    ) {
        composable(route = DetailsScreen.BookInfo.route,
            arguments = listOf(
                navArgument("str"){ NavType.StringType}
            )
        ) {back->
            val str = back.arguments?.getString("str")!!
            BookInfo(str)
        }
    }
}
sealed class DetailsScreen(val route: String) {
    object BookInfo : DetailsScreen(route = "Book_info/{str}")
}