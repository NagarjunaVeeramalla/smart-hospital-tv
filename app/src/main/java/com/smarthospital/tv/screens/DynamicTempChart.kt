package com.smarthospital.tv.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import com.smarthospital.tv.datamodels.TemperatureReading
import com.smarthospital.tv.ui.theme.VisionAppTheme

@Composable
fun TemperatureChart(
    readings: List<TemperatureReading>
) {
    val maxTemp = readings.maxOfOrNull { it.tempF } ?: 100.0

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Title
        Text(
            text = "Temperature",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Bars Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            readings.forEach { reading ->
                TemperatureBar(
                    reading = reading,
                    maxTemp = maxTemp
                )
            }
        }
    }
}

@Composable
fun TemperatureBar(
    reading: TemperatureReading,
    maxTemp: Double
) {
    val tempC = (reading.tempF - 32) * 5 / 9
    val barHeightRatio = reading.tempF / maxTemp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {

        // °F
        Text(
            text = "${reading.tempF}°F",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        // °C
        Text(
            text = String.format("%.1f°C", tempC),
            fontSize = 12.sp,
            color = Color.LightGray
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Bar
        Canvas(
            modifier = Modifier
                .height(120.dp)
                .width(24.dp)
        ) {
            val barHeight = size.height * barHeightRatio.toFloat()
            drawRect(
                color = Color(0xFF2F80ED), // blue like screenshot
                topLeft = Offset(0f, size.height - barHeight),
                size = androidx.compose.ui.geometry.Size(
                    width = size.width,
                    height = barHeight
                )
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Time
        Text(
            text = reading.time,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TemperatureChartPreview() {
    VisionAppTheme {
        TemperatureChart(
            readings = listOf(
                TemperatureReading("8am", 98.6),
                TemperatureReading("12pm", 98.6),
                TemperatureReading("3pm", 98.6)
            )
        )
    }
}

