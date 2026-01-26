package com.smarthospital.tv.myhealth.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smarthospital.tv.myhealth.datamodels.HospitalDataModel
import com.smarthospital.tv.myhealth.datamodels.toBloodPressureReadings
import com.smarthospital.tv.myhealth.datamodels.toHeartRateReadings
import com.smarthospital.tv.myhealth.datamodels.toTemperatureReadings
import com.smarthospital.tv.myhealth.ui.tvFocusDesign

@Composable
fun VitalsRow(
    vitals: List<HospitalDataModel.Vital>,
    onVitalClick: (HospitalDataModel.Vital) -> Unit = {}
) {
    LazyRow (
        contentPadding = PaddingValues(start = 12.dp, end = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        vitals.firstOrNull { it.displayName == "Blood Pressure" }?.let { bp ->
            item {
                PatientVitalCard(onClick = { onVitalClick(bp) }) {
                    BloodPressureChart(
                        readings = bp.toBloodPressureReadings()
                    )
                }
            }
        }

        vitals.firstOrNull { it.displayName == "Temperature" }?.let { temp ->
            item {
                PatientVitalCard(onClick = { onVitalClick(temp) }) {
                    TemperatureChart(
                        readings = temp.toTemperatureReadings()
                    )
                }
            }
        }

        vitals.firstOrNull { it.displayName == "Heart Rate" }?.let { hr ->
            item {
                PatientVitalCard(onClick = { onVitalClick(hr) }) {
                    HeartRateChart(
                        readings = hr.toHeartRateReadings()
                    )
                }
            }
        }
    }
}

@Composable
fun PatientVitalCard(
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .width(280.dp)
            .height(280.dp)
            .tvFocusDesign(width = 280.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}

@Preview(device = "id:tv_1080p")
@Composable
fun VitalsRowPreview() {
    val sampleVitals = listOf(
        // ❤️ HEART RATE
        HospitalDataModel.Vital(
            displayName = "Heart Rate",
            hasData = true,
            compoundSeparator = "",
            unit = "BPM",
            cardTitle = "Understanding Your Heart Rate",
            cardDescription = "Normal heart rate is 60–100 BPM.",
            measurements = listOf(
                HospitalDataModel.Measurement(
                    captureDateTime = "2025-09-25T08:35:17Z",
                    values = listOf(72.0)
                ),
                HospitalDataModel.Measurement(
                    captureDateTime = "2025-09-25T14:20:10Z",
                    values = listOf(78.0)
                ),
                HospitalDataModel.Measurement(
                    captureDateTime = "2025-09-25T20:35:17Z",
                    values = listOf(79.0)
                )
            )
        ),

        // 🩸 BLOOD PRESSURE
        HospitalDataModel.Vital(
            displayName = "Blood Pressure",
            hasData = true,
            compoundSeparator = "/",
            unit = "mmHg",
            cardTitle = "Understanding Your Blood Pressure",
            cardDescription = "Normal BP is less than 120/80.",
            measurements = listOf(
                HospitalDataModel.Measurement(
                    captureDateTime = "2025-09-25T08:30:00Z",
                    values = listOf(135.0, 88.0)
                ),
                HospitalDataModel.Measurement(
                    captureDateTime = "2025-09-25T13:15:00Z",
                    values = listOf(140.0, 92.0)
                ),
                HospitalDataModel.Measurement(
                    captureDateTime = "2025-09-25T20:35:17Z",
                    values = listOf(138.0, 90.0)
                )
            )
        ),

        // 🌡 TEMPERATURE
        HospitalDataModel.Vital(
            displayName = "Temperature",
            hasData = true,
            compoundSeparator = ",",
            unit = "°F",
            cardTitle = "Understanding Your Body Temperature",
            cardDescription = "Normal body temperature is around 98.6°F.",
            measurements = listOf(
                HospitalDataModel.Measurement(
                    captureDateTime = "2025-09-25T08:25:00Z",
                    values = listOf(98.4)
                ),
                HospitalDataModel.Measurement(
                    captureDateTime = "2025-09-25T13:10:00Z",
                    values = listOf(99.1)
                ),
                HospitalDataModel.Measurement(
                    captureDateTime = "2025-09-25T20:30:00Z",
                    values = listOf(101.2)
                )
            )
        )
    )
    VitalsRow(vitals = sampleVitals)
}