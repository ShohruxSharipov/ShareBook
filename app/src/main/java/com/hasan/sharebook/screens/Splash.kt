package com.hasan.sharebook.screens

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hasan.sharebook.R
import com.hasan.sharebook.database.Database
import com.hasan.sharebook.database.User
import com.hasan.sharebook.navigation.Screen
import com.hasan.sharebook.screens.ui.theme.Blue
import com.hasan.sharebook.screens.ui.theme.Blue_1
import com.hasan.sharebook.screens.ui.theme.ShareBookTheme
import kotlinx.coroutines.delay

@Composable
fun Splash(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val context = LocalContext.current
        LaunchedEffect(key1 = true) {
            delay(2000)
            navController.popBackStack()
            var user = Database.getSavedUser(context)
            if (user!="") {
                navController.navigate(Screen.Main.route)
            }else{
                navController.navigate(Screen.LogInScreen.route)
            }
        }
        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "share",
                    modifier = Modifier.wrapContentSize(),
                    color = Blue_1,
                    fontSize = 48.sp,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "book",
                    modifier = Modifier.wrapContentSize(),
                    color = Blue,
                    fontSize = 48.sp,
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.Serif
                )
            }
        }
    }
}



