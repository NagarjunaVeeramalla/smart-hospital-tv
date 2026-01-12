package com.smarthospital.tv.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import com.smarthospital.tv.datamodels.BloodPressureReading
import com.smarthospital.tv.ui.theme.VisionAppTheme

@Composable
fun BloodPressureChart(
    readings: List<BloodPressureReading>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Title
        Text(
            text = "Blood Pressure (mmHg)",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            readings.forEach { reading ->
                BloodPressureBar(reading)
            }
        }
    }
}

@Composable
fun BloodPressureBar(
    reading: BloodPressureReading
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {

        // Systolic
        Text(
            text = reading.systolic.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Canvas(
            modifier = Modifier
                .height(100.dp)
                .width(24.dp)
        ) {
            val topY = 16.dp.toPx()
            val bottomY = size.height - 16.dp.toPx()
            val centerX = size.width / 2

            // Top dot
            drawCircle(
                color = Color(0xFFFFC107), // yellow
                radius = 6.dp.toPx(),
                center = Offset(centerX, topY)
            )

            // Line
            drawLine(
                color = Color(0xFFFFC107),
                start = Offset(centerX, topY),
                end = Offset(centerX, bottomY),
                strokeWidth = 3.dp.toPx(),
                cap = StrokeCap.Round
            )

            // Bottom dot
            drawCircle(
                color = Color(0xFFFFC107),
                radius = 6.dp.toPx(),
                center = Offset(centerX, bottomY)
            )
        }

        // Diastolic
        Text(
            text = reading.diastolic.toString(),
            fontSize = 14.sp,
            color = Color.LightGray
        )

        // Time
        Text(
            text = reading.time,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BloodPressureChartPreview() {
    VisionAppTheme {
        BloodPressureChart(
            readings = listOf(
                BloodPressureReading("8am", 140, 90),
                BloodPressureReading("12pm", 130, 85),
                BloodPressureReading("3pm", 120, 80)
            )
        )
    }
}
