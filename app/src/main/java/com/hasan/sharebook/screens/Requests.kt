package com.hasan.sharebook.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hasan.sharebook.database.Book
import com.hasan.sharebook.database.Database
import com.hasan.sharebook.database.Request


@Composable
fun Requests() {
    val requests = remember {
        mutableStateOf(listOf<Request>())
    }
    val context = LocalContext.current
    val user = Database.getSavedUser(context)
    var book by remember {
        mutableStateOf(Book("", "", "", "", ""))
    }
    Database.getRequests(user) { item ->
        requests.value = item
        Log.d("Tag3", "Requests: ${requests}")
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            if (requests.value.size != 0) {
                LazyColumn {
                    Log.d("TAG", "Requests: jghkjh")
                    items(requests.value) { it ->
                        Database.getBook(it.bookId.toString()) {
                            book = it
                        }
                        Log.d("TAG4", "Requests: ${book}")
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(10.dp),
                            shape = RoundedCornerShape(25.dp),
                            backgroundColor = Color(153, 217, 234)
                        ) {
                            Column() {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                ) {
                                    Text(
                                        text = "${book.name}",
                                        color = Color.White,
                                        fontSize = 24.sp
                                    )
                                    Text(
                                        text = "Murojaat qiluvchi: ${it.from?.dropLast(4)}",
                                        color = Color.White,
                                        fontSize = 20.sp
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    Modifier
                                        .padding(bottom = 10.dp)
                                        .fillMaxWidth()
                                        .padding(end = 150.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    OutlinedButton(
                                        modifier = Modifier
                                            .padding(horizontal = 10.dp),
                                        onClick = { },
                                        border = BorderStroke(width = 2.dp, color = Color.Red)
                                    ) {
                                        Text(
                                            text = "Decline",
                                            color = Color.Red
                                        )
                                    }
                                    OutlinedButton(
                                        modifier = Modifier
                                            .padding(horizontal = 5.dp),
                                        onClick = {
                                            Database.updateBookOwner(book.bookId!!, it.from!!)
                                            Database.updateRequest(it)
                                            Database.getRequests(user) { item ->
                                                requests.value = item
                                            }
                                        },
                                        border = BorderStroke(
                                            width = 2.dp,
                                            color = Color(32, 166, 71)
                                        )
                                    ) {
                                        Text(
                                            text = "Accept",
                                            color = Color(32, 166, 71)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Row(modifier = Modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            modifier = Modifier.size(200.dp),
                            painter = painterResource(id = com.hasan.sharebook.R.drawable.img_2),
                            contentDescription = ""
                        )
                        Text(text = "Hech narsa topilmadi")
                    }
                }


            }
        }
    }
}



