package com.smarthospital.tv.myhealth.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import com.smarthospital.tv.R
import com.smarthospital.tv.myhealth.datamodels.BloodPressureReading
import com.smarthospital.tv.myhealth.ui.theme.SmartHospitalAppTheme

@Composable
fun BloodPressureChart(
    readings: List<BloodPressureReading>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Chart Header
        ChartHeader(
            title = "Blood Pressure (mmHg)",
            iconRes = R.mipmap.ic_launcher
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Bottom
        ) {
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

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider(color = Color.White)

        Spacer(modifier = Modifier.height(8.dp))

        // Time labels aligned with bars
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            readings.forEach { reading ->
                Text(
                    text = reading.time,
                    fontSize = 10.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Light,// Match bar container width for alignment
                    textAlign = TextAlign.Center
                )
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
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.width(40.dp)
    ) {
        // Systolic
        Text(
            text = reading.systolic.toString(),
            fontSize = 16.sp,
            color = Color.White
        )

        Canvas(
            modifier = Modifier
                .height(100.dp)
                .width(24.dp)
        ) {
            val radiusPx = 6.dp.toPx()
            val topY = 16.dp.toPx()
            val bottomY = size.height - 16.dp.toPx()
            val centerX = size.width / 2

            val color = Color(0xFFFFC107) // yellow/orange

            // Top dot
            drawCircle(
                color = color,
                radius = radiusPx,
                center = Offset(centerX, topY),
                style = Stroke(width = 2.dp.toPx())
            )

            // Line (adjusted to touch circumference)
            drawLine(
                color = color,
                start = Offset(centerX, topY + radiusPx),
                end = Offset(centerX, bottomY - radiusPx),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round
            )

            // Bottom dot
            drawCircle(
                color = color,
                radius = radiusPx,
                center = Offset(centerX, bottomY),
                style = Stroke(width = 2.dp.toPx())
            )
        }

        // Diastolic
        Text(
            text = reading.diastolic.toString(),
            fontSize = 14.sp,
            color = Color.LightGray
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun BloodPressureChartPreview() {
    SmartHospitalAppTheme {
        PatientVitalCard {
            BloodPressureChart(
                readings = listOf(
                    BloodPressureReading("9:00 Am", 140, 95),
                    BloodPressureReading("12:00 pm", 130, 90),
                    BloodPressureReading("3:00 pm", 120, 95)
                )
            )
        }
    }
}
