package com.hasan.sharebook.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hasan.sharebook.screens.LogIn
import com.hasan.sharebook.screens.SignIn
import com.hasan.sharebook.screens.Splash

fun NavGraphBuilder.authGraph(navController: NavController){
    navigation(startDestination = Screen.SplashScreen.route, route = Screen.Auth.route) {
        composable(route = Screen.SplashScreen.route) {
            Splash(navController)
        }
        composable(route = Screen.LogInScreen.route) {
            LogIn(navController)
        }
        composable(route = Screen.SignInScreen.route) {
            SignIn(navController)
        }
    }
}