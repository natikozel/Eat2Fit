package com.example.finalproj.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalproj.R


@Composable
fun RoundFrame(data: String, title: String) {
    Box(
        Modifier
            .shadow(10.0.dp, shape = RoundedCornerShape(16.0.dp))
            .clip(RoundedCornerShape(16.0.dp))
            .size(95.0.dp, 65.0.dp)
            .background(Color(1.0f, 1.0f, 1.0f, 1.0f))
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                10.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        )
        {


            Text(
                text = data,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 21.sp,
                    fontFamily = FontFamily(Font(R.font.karla_bold)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF92A3FD),
                )
            )
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.karla_bold)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF7B6F72),
                )
            )
        }
    }
}