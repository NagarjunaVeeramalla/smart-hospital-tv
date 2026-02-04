package com.smarthospital.tv.myhealth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import com.smarthospital.tv.myhealth.ui.theme.SmartHospitalAppTheme
import com.smarthospital.tv.myhealth.ui.tvFocusDesign

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun PainRatingScreen(
    imageUrl: String,
    title: String,
    description: String,
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(50.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Left Side: Text and Back Button
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(end = 40.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = title,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = description,
                color = Color.White,
                fontSize = 16.sp,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onBackClick,
                modifier = Modifier.tvFocusDesign(240.dp),
                shape = ButtonDefaults.shape(shape = RectangleShape),
                colors = ButtonDefaults.colors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text("Return to previous screen")
            }

            Spacer(modifier = Modifier.height(40.dp))
        }

        // Right Side: Image
        Box(
            modifier = Modifier
                .width(400.dp) // Adjusted width for better landscape image view
                .height(300.dp)
                .align(Alignment.CenterVertically)
                .background(Color.DarkGray) // Placeholder background
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview(device = "id:tv_1080p")
@Composable
fun PainRatingScreenPreview() {
    SmartHospitalAppTheme {
        PainRatingScreen(
            imageUrl = "https://www.verywellhealth.com/thmb/62y6-uCbnwM72B-1zZJod7s2vj8=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/pain-scales-and-faces-rating-charts-5213233-01-d3a33c2a933f465080e8c56c28905a39.jpg",
            title = "Rate Your Pain",
            description = "Please select the face that best represents your current pain level. 0 is no pain, and 10 is the worst possible pain.",
            onBackClick = {}
        )
    }
}