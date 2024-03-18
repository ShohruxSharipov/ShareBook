package com.hasan.sharebook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.hasan.sharebook.screens.BookInfo
import com.hasan.sharebook.screens.Home
import com.hasan.sharebook.screens.Info
import com.hasan.sharebook.screens.LogIn
import com.hasan.sharebook.screens.Main
import com.hasan.sharebook.screens.Profile
import com.hasan.sharebook.screens.SignIn
import com.hasan.sharebook.screens.Splash

@Composable
fun Navigation(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = Screen.Auth.route) {

        authGraph(navHostController)
        composable(route = Screen.Main.route){
            Main()
        }

    }
}