package com.hasan.sharebook.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hasan.sharebook.R
import com.hasan.sharebook.database.Book
import com.hasan.sharebook.database.Database
import com.hasan.sharebook.database.Database.Companion.ref
import java.util.Locale
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Add() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 24.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            val context = LocalContext.current
            var selectedImageUris by remember {
                mutableStateOf<List<Uri>>(emptyList())
            }
            Text(text = "Rasmlar : ")
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {


                val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickMultipleVisualMedia(),
                    onResult = { it -> selectedImageUris = it }
                )


                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(selectedImageUris) {
                        AsyncImage(
                            model = it,
                            contentDescription = null,
                            modifier = Modifier.size(80.dp)
                        )
                    }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Card(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clickable {
                                        multiplePhotoPickerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(
                                        174,
                                        175,
                                        175,
                                        255
                                    )
                                )
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "+", fontSize = 40.sp, color = Color.White)
                                    Text(text = "add immage", fontSize = 10.sp, color = Color.White)
                                }

                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.width(5.dp))
            }

            Spacer(modifier = Modifier.height(10.dp))
            val value = remember {
                mutableStateOf("")
            }
            Text(text = "Kitob nomi : ")
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = value.value,
                onValueChange = { newtext -> value.value = newtext },
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Genre : ")
            Spacer(modifier = Modifier.height(10.dp))
            var isExpanded by remember {
                mutableStateOf(false)
            }
            val list = remember {
                mutableListOf("Badiiy", "Ilmiy", "Chet Adabiyoti", "Klassik Adabiyot")
            }
            var genre by remember {
                mutableStateOf("")
            }

            ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded = it }) {
                OutlinedTextField(
                    value = genre,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }) {
                    for (i in list) {
                        DropdownMenuItem(text = { Text(text = i) }, onClick = {
                            genre = i
                            isExpanded = false
                        })
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            val description = remember {
                mutableStateOf("")
            }
            Text(text = "Qo'shimcha ma'lumot : ")
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = description.value,
                onValueChange = { newtext -> description.value = newtext },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                placeholder = {
                    Text(
                        text = "Qo'shimcha ma'lumotlar ...",
                        color = Color.Gray,
                        fontSize = 15.sp
                    )
                })

            Row(verticalAlignment = Alignment.Bottom, modifier = Modifier.fillMaxHeight()) {
                Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = {
                        val random_book_id = ref.push().key.toString()
                        val userid = Database.getSavedUser(context)
                        val book =
                            Book(
                                random_book_id,
                                value.value,
                                description.value,
                                genre,
                                userid
                            )


                        Database.sendBook(book)
                        selectedImageUris.forEach {
                            Database.sendBookPhoto(it, random_book_id, context)
                        }


                    }) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}

