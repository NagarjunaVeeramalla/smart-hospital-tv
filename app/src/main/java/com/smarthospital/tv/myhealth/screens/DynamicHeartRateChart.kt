package com.smarthospital.tv.myhealth.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import com.smarthospital.tv.R
import com.smarthospital.tv.myhealth.datamodels.HeartRateReading
import com.smarthospital.tv.myhealth.ui.theme.SmartHospitalAppTheme
import kotlin.math.sqrt

@Composable
fun HeartRateChart(
    readings: List<HeartRateReading>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        ChartHeader(
            title = "Heart Rate (BPM)",
            iconRes = R.mipmap.ic_launcher // Using a heart icon
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Bottom) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                HeartRateLineChart(
                    readings = readings,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        HorizontalDivider(color = Color.White)

        Spacer(modifier = Modifier.height(8.dp))

        // Time labels aligned with chart points
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            readings.forEach { reading ->
                Text(
                    text = reading.time,
                    fontSize = 10.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
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
    if (readings.isEmpty()) return

    Canvas(modifier = modifier) {

        val maxVal = readings.maxOfOrNull { it.bpm } ?: 100
        val minVal = readings.minOfOrNull { it.bpm } ?: 0

        // Visual padding for slope visibility
        val chartMax = maxVal + 10
        val chartMin = (minVal - 10).coerceAtLeast(0)
        val range = (chartMax - chartMin).coerceAtLeast(1)

        val segmentWidth = size.width / readings.size
        fun getX(index: Int) = index * segmentWidth + segmentWidth / 2

        fun y(value: Int): Float =
            size.height - ((value - chartMin).toFloat() / range) * size.height

        val linePathColor = Color(0xFFD96666)
        val radiusPx = 5.dp.toPx()

        // Line
        if (readings.size > 1) {
            for (i in 0 until readings.size - 1) {
                val x1 = getX(i)
                val y1 = y(readings[i].bpm)
                val x2 = getX(i + 1)
                val y2 = y(readings[i + 1].bpm)

                val dx = x2 - x1
                val dy = y2 - y1
                val distance = sqrt(dx * dx + dy * dy)
                if (distance == 0f) continue

                val unitX = dx / distance
                val unitY = dy / distance

                val adjustedStartX = x1 + unitX * radiusPx
                val adjustedStartY = y1 + unitY * radiusPx
                val adjustedEndX = x2 - unitX * radiusPx
                val adjustedEndY = y2 - unitY * radiusPx

                drawLine(
                    color = linePathColor,
                    start = Offset(adjustedStartX, adjustedStartY),
                    end = Offset(adjustedEndX, adjustedEndY),
                    strokeWidth = 2.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }

        // Dots + values
        readings.forEachIndexed { index, reading ->
            val x = getX(index)
            val yPos = y(reading.bpm)

            drawCircle(
                color = linePathColor,
                radius = radiusPx,
                center = Offset(x, yPos),
                style = Stroke(width = 3.dp.toPx())
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
        PatientVitalCard() {
            HeartRateChart(
                readings = listOf(
                    HeartRateReading("9am", 60),
                    HeartRateReading("12pm", 75),
                    HeartRateReading("3pm", 61)
                )
            )
        }
    }
}
