package com.smarthospital.tv.myhealth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.smarthospital.tv.myhealth.ui.theme.SmartHospitalAppTheme
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ScheduleDetailsScreen(
    title: String,
    description: String,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = description,
            color = Color.White,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            modifier = Modifier.padding(horizontal = 60.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onBackClick,
            shape = ButtonDefaults.shape(shape = RectangleShape),
            colors = ButtonDefaults.colors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Text("Return to previous screen")
        }
    }
}

@Preview(device = "id:tv_1080p")
@Composable
fun ScheduleDetailsScreenPreview() {
    SmartHospitalAppTheme {
        ScheduleDetailsScreen(
            title = "Display Device",
            description = "If you own an apple device you can share your screen with the TV. Are you attempting to screen share from an iPhone or a Mac?",
            onBackClick = {}
        )
    }
}
