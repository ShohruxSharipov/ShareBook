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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hasan.sharebook.database.Database
import com.hasan.sharebook.database.User
import com.hasan.sharebook.navigation.Screen
import com.hasan.sharebook.screens.ui.theme.Blue
import com.hasan.sharebook.screens.ui.theme.Blue_1
import com.hasan.sharebook.screens.ui.theme.ShareBookTheme

@Composable
fun SignIn(navController: NavController) {
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
            val phone = remember {
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
                    text = "Sign in",
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
                    placeholder = { Text(text = "Passwod") }, maxLines = 1,
                    isError = password.value.length <= 8,
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 20.dp),
                    value = phone.value,
                    onValueChange = { newText ->
                        phone.value = newText
                    },
                    label = { Text(text = "Phone Number") },
                    placeholder = { Text(text = "Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    maxLines = 1
                )

                GradientButton(
                    text = "ENTER",
                    gradient = Brush.horizontalGradient(listOf(Blue_1, Blue)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(horizontal = 20.dp)
                        .clickable {
                            if (username.value.isNotBlank() && password.value.isNotBlank() && phone.value.isNotBlank()) {
                                val user = User(
                                    username = username.value,
                                    password = password.value,
                                    phone_number = phone.value
                                )
                                navController.navigate(Screen.Main.route)
                                Database.setUser(user)

                            } else {
                                Toast
                                    .makeText(ctx, "Ma'lumotlarni kiriting", Toast.LENGTH_SHORT)
                                    .show()
                            }

                        }
                )

            }
        }
    }

}