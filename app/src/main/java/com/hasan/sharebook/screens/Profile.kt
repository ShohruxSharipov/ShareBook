package com.hasan.sharebook.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hasan.sharebook.R
import com.hasan.sharebook.database.Database
import com.hasan.sharebook.database.User
import com.hasan.sharebook.navigation.Screen

@Composable
fun Profile(navController: NavController) {
    val context = LocalContext.current
    Surface(modifier = Modifier.fillMaxSize()) {
        val user = remember {
            mutableStateOf(User("","","",""))
        }
        Database.getUser(Database.getSavedUser(context)) {
            user.value = it
            Log.d("TAG12", "Profile: ${it}")
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding()
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            Card(
                modifier = Modifier.padding(top = 30.dp),
                shape = RoundedCornerShape(50.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_1),
                    contentDescription = "",
                    modifier = Modifier.size(100.dp),
                    alignment = Alignment.Center
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            var user_name = user.value.username
            var user_number = user.value.phone_number
            var user_location = user.value.description
            OutlinedTextField(
                value = user_name!!,
                onValueChange = { newText -> user_name = newText },
                readOnly = true,
                label = {
                    Text(
                        text = "Name"
                    )
                }, modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(
                value = user_number!!,
                readOnly = true,
                onValueChange = { newText -> user_number = newText },
                label = { Text(text = "Phone number", fontSize = 18.sp) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(
                value = user_location!!,
                readOnly = true,
                onValueChange = { newText -> user_location = newText },
                label = { Text(text = "Location", fontSize = 18.sp) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                Database.clearData(context)

            }) {
                Text(text = "Log out")
            }
        }
    }
}