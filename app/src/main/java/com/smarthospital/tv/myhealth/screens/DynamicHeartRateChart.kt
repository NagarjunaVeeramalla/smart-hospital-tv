package com.smarthospital.tv.myhealth.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import com.smarthospital.tv.R
import com.smarthospital.tv.myhealth.datamodels.HeartRateReading
import com.smarthospital.tv.myhealth.ui.theme.SmartHospitalAppTheme

@Composable
fun HeartRateChart(
    readings: List<HeartRateReading>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        ChartHeader(
            title = "Heart Rate (BPM)",
            iconRes = R.mipmap.ic_launcher
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            HeartRateLineChart(
                readings = readings,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Time labels aligned with chart points
        // Using weights to ensure labels are centered under the dots
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            readings.forEachIndexed { index, reading ->
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = when(index) {
                        0 -> Alignment.CenterStart
                        readings.size - 1 -> Alignment.CenterEnd
                        else -> Alignment.Center
                    }
                ) {
                    Text(
                        text = reading.time,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Light,
                        modifier = if (index == 0) Modifier.padding(start = 0.dp) else Modifier 
                    )
                }
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
        val radiusPx = 5.dp.toPx()
        for (i in 0 until readings.size - 1) {
            val x1 = i * xStep
            val y1 = y(readings[i].bpm)
            val x2 = (i + 1) * xStep
            val y2 = y(readings[i + 1].bpm)

            val dx = x2 - x1
            val dy = y2 - y1
            val length = Math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()

            if (length > radiusPx * 2) {
                val unitDx = dx / length
                val unitDy = dy / length

                drawLine(
                    color = Color.Red,
                    start = Offset(x1 + radiusPx * unitDx, y1 + radiusPx * unitDy),
                    end = Offset(x2 - radiusPx * unitDx, y2 - radiusPx * unitDy),
                    strokeWidth = 3.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }

        // Dots + values
        readings.forEachIndexed { index, reading ->
            val x = index * xStep
            val yPos = y(reading.bpm)

            drawCircle(
                color = Color.Red,
                radius = radiusPx,
                center = Offset(x, yPos),
                style = Stroke(width = 2.dp.toPx())
            )

            drawContext.canvas.nativeCanvas.drawText(
                reading.bpm.toString(),
                x,
                yPos - 12.dp.toPx(),
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


@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun HeartRateChartPreview() {
    SmartHospitalAppTheme {
        HeartRateChart(
            readings = listOf(
                HeartRateReading("9am", 80),
                HeartRateReading("12pm", 75),
                HeartRateReading("3pm", 61)
            )
        )
    }
}