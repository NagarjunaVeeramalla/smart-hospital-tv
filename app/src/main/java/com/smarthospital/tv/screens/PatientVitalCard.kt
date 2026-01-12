package com.smarthospital.tv.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.smarthospital.tv.ui.tvFocusDesign

@Composable
fun PatientVitalCard(
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .width(360.dp)
            .height(260.dp)
            .tvFocusDesign(width = 360.dp)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        content()
    }
}
