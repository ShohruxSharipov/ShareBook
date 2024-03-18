package com.hasan.sharebook.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String, val title: String = "",
    val icon: ImageVector? = null
) {

    data object HomeScreen :
        Screen(route = "home_screen", title = "Home", icon = Icons.Default.Home)

    data object LogInScreen : Screen(route = "login_screen")
    data object SignInScreen : Screen(route = "signin_screen")
    data object SplashScreen : Screen(route = "splash_screen")
    data object Main : Screen(route = "main_screen")
    data object ProfileScreen :
        Screen(route = "profile_screen", title = "Profile", icon = Icons.Default.AccountCircle)
    data object ChatScreen :
        Screen(route = "chat_screen", title = "Chat", icon = Icons.Default.MailOutline)
    data object AddScreen :
        Screen(route = "add_screen", title = "Add", icon = Icons.Default.AddCircle)

    data object Auth : Screen(route = "auth_route")
    data object Details : Screen(route = "details_route")
    data object Info : Screen(route = "info_route")
    data object Requests : Screen(route = "requests_route")
}