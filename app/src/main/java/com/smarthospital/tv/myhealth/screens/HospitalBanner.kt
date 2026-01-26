package com.smarthospital.tv.myhealth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun HospitalBanner(
    imageUrl: String
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Hospital Banner",
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .background(Color.DarkGray),
        contentScale = ContentScale.Crop
    )
}
