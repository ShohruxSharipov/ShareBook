package com.hasan.sharebook.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hasan.sharebook.database.Book
import com.hasan.sharebook.database.Database
import com.hasan.sharebook.database.Request
import com.hasan.sharebook.navigation.MainNavGraph
import com.hasan.sharebook.navigation.Screen
import com.hasan.sharebook.screens.ui.theme.Blue
import com.hasan.sharebook.screens.ui.theme.Blue_1
import com.hasan.sharebook.screens.ui.theme.ShareBookTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(navController: NavHostController = rememberNavController()) {
    ShareBookTheme {
        val requests = remember {
            mutableStateOf(listOf<Request>())
        }
        val context = LocalContext.current
        val user = Database.getSavedUser(context)
        Database.getRequests(user) { item ->
            requests.value = item
            Log.d("Tag3", "Requests: ${requests}")
        }
        Scaffold(bottomBar = {
            BottomBar(navController = navController)
        }, topBar = {
            CenterAlignedTopAppBar(title = {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("ShareBook", fontSize = 24.sp)
                }
            },

                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Menu, contentDescription = "Navigation Icon")
                    }
                }, actions = {
                    IconButton(onClick = { navController.navigate(Screen.Requests.route) }) {
                        if (requests.value.size != 0) {
                            BadgedBox(badge = { Badge { Text("${requests.value.size}") } }) {
                                Icon(Icons.Outlined.Notifications, contentDescription = "")
                            }
                        } else {
                            Icon(Icons.Outlined.Notifications, contentDescription = "")
                        }
                    }
                    IconButton(onClick = { navController.navigate(Screen.Info.route) }) {
                        Icon(Icons.Outlined.Info, contentDescription = "")
                    }
                })
        }) {
            Column(modifier = Modifier.padding(it)) {
                MainNavGraph(navController = navController)
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    var screens = listOf(
        Screen.HomeScreen,
        Screen.ChatScreen,
        Screen.AddScreen,
        Screen.ProfileScreen
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(modifier = Modifier.background(Blue_1), backgroundColor = Blue_1) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }

}

@Composable
fun RowScope.AddItem(
    screen: Screen,
    currentDestination: NavDestination?,
    navController: NavController
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route;
    val selected = currentRoute == screen.route
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            screen.icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = "Navigation Icon",
                    tint = if (selected) Blue else Color(14, 44, 79, 100)
                )
            }
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = if (selected) Blue else Color(14, 44, 79, 100),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}