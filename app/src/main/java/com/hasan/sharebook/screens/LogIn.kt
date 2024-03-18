package com.hasan.sharebook.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.hasan.sharebook.database.Database
import com.hasan.sharebook.navigation.Screen
import com.hasan.sharebook.screens.ui.theme.Blue
import com.hasan.sharebook.screens.ui.theme.Blue_1
import com.hasan.sharebook.screens.ui.theme.ShareBookTheme


@Composable
fun LogIn(navController: NavController) {
    val ctx = LocalContext.current
    ShareBookTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val username = remember {
                mutableStateOf("")
            }
            val password = remember {
                mutableStateOf("")
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(top = 100.dp)
                        .align(Alignment.CenterHorizontally)
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
                Text(
                    text = "Log in",
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                        .padding(bottom = 80.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp,
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.Serif
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 10.dp),
                    value = username.value,
                    onValueChange = { newText ->
                        username.value = newText
                    },
                    label = { Text(text = "Username") },
                    placeholder = { Text(text = "Username") }, maxLines = 1
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 20.dp),
                    value = password.value,
                    onValueChange = { newText ->
                        password.value = newText
                    },
                    label = { Text(text = "Passwod") },
                    placeholder = { Text(text = "Passwod") }, maxLines = 1
                )


                GradientButton(
                    text = "Log in",
                    gradient = Brush.horizontalGradient(listOf(Blue_1, Blue)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(horizontal = 20.dp)
                        .clickable {
                            Database.checkUser(username.value, password.value) {
                                if (it == "Logged in Successfully") {
                                    Toast
                                        .makeText(ctx, it, Toast.LENGTH_SHORT)
                                        .show()
                                    Database.saveUser(ctx,"${username.value}_123")
                                    navController.navigate(Screen.Main.route)

                                } else {
                                    Toast
                                        .makeText(ctx, "Ma'lumotlar xato", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }

                        }
                )
                Row(modifier = Modifier.padding(top = 10.dp)) {
                    Text(text = "Akkountingiz yo'qmi?", color = Blue_1)
                    Text(text = "Ro'yxatdan o'ting", color = Blue, modifier = Modifier.clickable {
                        navController.navigate(Screen.SignInScreen.route)
                    })

                }
            }
        }
    }

}

@Composable
fun GradientButton(
    text: String,
    gradient: Brush,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(),
        onClick = { onClick() },
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .then(modifier),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = text)
        }
    }
}

