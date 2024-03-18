package com.hasan.sharebook.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hasan.sharebook.screens.ui.theme.Blue_1

@Composable
fun Info() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        Column {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(text = "ShareBook FAQ", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                Text(text = "by ShareBook organisers", fontStyle = FontStyle.Italic)
                Spacer(modifier = Modifier.height(30.dp))
                Card(modifier = Modifier.height(130.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Card(
                            modifier = Modifier
                                .width(2.dp)
                                .fillMaxSize(),
                            colors = CardDefaults.cardColors(containerColor = Blue_1)
                        ) {}
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            fontStyle = FontStyle.Italic,
                            text = "ShareBook FAQ sizga bu loyiha haqidagi tez-tez so'rab turiladigan savollarga javoblarni taqdim qiladigan band hisoblanadi. Siz quyidagi link orqali o'tib o'zingizni qiziqtirgan savollarga javob olishingiz mumkin !",
                            textAlign = TextAlign.Justify
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                Card(modifier = Modifier.height(130.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Card(
                            modifier = Modifier
                                .width(2.dp)
                                .fillMaxSize(),
                            colors = CardDefaults.cardColors(containerColor = Blue_1)
                        ) {}
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            fontStyle = FontStyle.Italic,
                            text = "ShareBook rivojlanishda va yangi xususiyatlar qo'shishda davom etadi. Shu sababli bu xujjat bazi eskirgan ma'lumotlar bergan bo'lishi mumkin. Biz yangilangan FAQni yakunlashga harakat qilamiz ! ",
                            textAlign = TextAlign.Justify
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
                Text(text = "General", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    Text(
                        text = "* What is ShareBook",
                        color = Color.Blue,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "* Who is it for", color = Color.Blue, fontWeight = FontWeight.Bold)
                    Text(text = "* Last update", color = Color.Blue, fontWeight = FontWeight.Bold)
                    Text(
                        text = "* News in ShareBook",
                        color = Color.Blue,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}