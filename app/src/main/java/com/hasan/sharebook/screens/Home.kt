package com.hasan.sharebook.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.hasan.sharebook.R
import com.hasan.sharebook.database.Book
import com.hasan.sharebook.database.Database
import com.hasan.sharebook.screens.ui.theme.Blue
import com.hasan.sharebook.screens.ui.theme.Blue_1

@Composable
fun Home(navController: NavHostController) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp)
        ) {
            val search = remember {
                mutableStateOf("")
            }
            var books  by remember {
                mutableStateOf<List<Book>>(emptyList())
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = search.value,
                onValueChange = { newText ->
                    search.value = newText
                },
                placeholder = { Text(text = "Search anything ...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = ""
                    )
                })

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Text(
                    text = "Categories",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Database.getBooks { list ->
                books = list
            }
            LazyRow {
                items(listOf("Hammasi","Badiiy", "Ilmiy", "Chet Adabiyoti", "Klassik Adabiyot")) {
                    Surface() {
                        OutlinedButton(
                            onClick = {
                                books = emptyList()
                                Database.getCategorisedBooks(it) { list ->
                                    books = list
                                }
                                Log.d("TAG", "CategoryView: $books")

                            },
                            border = BorderStroke(
                                2.dp, Brush.horizontalGradient(
                                    colors = listOf(
                                        Blue_1, Blue
                                    )
                                )
                            ),
                            modifier = Modifier.padding(end = 10.dp)
                        ) {
                            Text(text = it, color = Color.Black, modifier = Modifier)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Text(
                    text = "Recomended for you",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(books) {
                    Column(modifier = Modifier.clickable {
                        navController.navigate("Book_info/${it.bookId}")
                    }, horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
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
        }
    }
}