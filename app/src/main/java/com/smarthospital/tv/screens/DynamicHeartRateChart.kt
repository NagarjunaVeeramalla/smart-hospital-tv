package com.smarthospital.tv.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import com.smarthospital.tv.datamodels.HeartRateReading
import com.smarthospital.tv.ui.theme.VisionAppTheme

@Composable
fun HeartRateChart(
    readings: List<HeartRateReading>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Heart Rate (BPM)",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            HeartRateLineChart(
                readings = readings,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // ✅ Time labels mapped to SAME X positions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            readings.forEach { reading ->
                Text(
                    text = reading.time,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}



@Composable
fun HeartRateLineChart(
    readings: List<HeartRateReading>,
    modifier: Modifier = Modifier
) {
    if (readings.size < 2) return

    Canvas(modifier = modifier) {

        val maxVal = readings.maxOf { it.bpm }
        val minVal = readings.minOf { it.bpm }

        // Visual padding for slope visibility
        val chartMax = maxVal + 10
        val chartMin = (minVal - 10).coerceAtLeast(0)
        val range = (chartMax - chartMin).coerceAtLeast(1)

        val xStep = size.width / (readings.size - 1)

        fun y(value: Int): Float =
            size.height - ((value - chartMin).toFloat() / range) * size.height

        // Line
        for (i in 0 until readings.size - 1) {
            drawLine(
                color = Color.Red,
                start = Offset(i * xStep, y(readings[i].bpm)),
                end = Offset((i + 1) * xStep, y(readings[i + 1].bpm)),
                strokeWidth = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        // Dots + values
        readings.forEachIndexed { index, reading ->
            val x = index * xStep
            val yPos = y(reading.bpm)

            drawCircle(
                color = Color.Red,
                radius = 5.dp.toPx(),
                center = Offset(x, yPos)
            )

            drawContext.canvas.nativeCanvas.drawText(
                reading.bpm.toString(),
                x,
                yPos - 10.dp.toPx(),
                android.graphics.Paint().apply {
                    color = android.graphics.Color.WHITE
                    textAlign = android.graphics.Paint.Align.CENTER
                    textSize = 14.sp.toPx()
                    isAntiAlias = true
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HeartRateChartPreview() {
    VisionAppTheme {
        HeartRateChart(
            readings = listOf(
                HeartRateReading("8am", 60),
                HeartRateReading("12pm", 75),
                HeartRateReading("3pm", 61)
            )
        )
    }
}