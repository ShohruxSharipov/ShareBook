package com.hasan.sharebook.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hasan.sharebook.R
import com.hasan.sharebook.database.Book
import com.hasan.sharebook.database.Database
import com.hasan.sharebook.database.Request
import com.hasan.sharebook.database.User
import com.hasan.sharebook.screens.ui.theme.Blue
import com.hasan.sharebook.screens.ui.theme.Blue_1

@Composable
fun BookInfo(str: String) {
    Surface(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(
            rememberScrollState()
        )) {
        var book:Book by remember {
            mutableStateOf<Book>(Book("name","name","name","name","name"))
        }
        var user:User by remember {
            mutableStateOf<User>(User("name","name","name","name","name"))
        }
        var recomendations by remember {
            mutableStateOf(emptyList<Book>())
        }
        val context = LocalContext.current
        Database.getBook(str) { it ->
            book = it
        }
        Database.getUser(book.owner!!) { it ->
            user = it
        }

        Database.getCategorisedBooks(book.genre!!) { it ->
            recomendations = it
        }

        Log.d("TAG", "BookInfo: ${book.name}")
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = "",
                    modifier = Modifier
                        .height(200.dp)
                        .width(150.dp)
                )
                Text(text = book.name!!, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(20.dp))
            Column {
                Text(text = "Qo'shimcha ma'lumot : ")
                Text(
                    text = book.description.toString(),
                    fontSize = 18.sp, fontStyle = FontStyle.Italic, color = Color.Gray
                )
            }


            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Janr : ")
                Text(text = book.genre.toString(), color = Color.Black)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Owner : ")
                Text(text = user.username.toString(), color = Color.Black)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Tel. raqam : ")
                Text(text = "${user.phone_number}")

            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Recomendations : ")
            Spacer(modifier = Modifier.height(20.dp))

            LazyRow() {
                items(recomendations) {
                    Column(modifier = Modifier.size(80.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            modifier = Modifier.fillMaxWidth(),
                            painter = painterResource(id = R.drawable.img),
                            contentDescription = ""
                        )
                        Text(text = it.name!!)
                        Text(
                            text = it.genre!!,
                            fontStyle = FontStyle.Italic,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(30.dp))

                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedButton(modifier = Modifier.weight(1f), onClick = {
                    Toast.makeText(context, "Bu funksiya tez orada qo'shiladi", Toast.LENGTH_SHORT).show()
                }, border = BorderStroke(width = 2.dp,color = Blue_1)) {
                    Text(text = "Message", color = Blue)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(modifier = Modifier.weight(1f),onClick = {
                    val from = Database.getSavedUser(context)
                    val to = book.owner
                    val bookId = book.bookId
                    val request = Request(from,to, bookId)
                    if (from!=to) {
                        Database.sendRequest(request)
                    }else{
                        Toast.makeText(context, "Bu sizning kitobingiz", Toast.LENGTH_SHORT).show()
                    }
                }, colors = ButtonDefaults.buttonColors(containerColor = Blue_1)) {
                    Text(text = "Order")
                }
            }


        }
    }
}