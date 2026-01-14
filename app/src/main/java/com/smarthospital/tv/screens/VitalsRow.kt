package com.smarthospital.tv.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyRow
import com.smarthospital.tv.datamodels.HospitalDataModel
import com.smarthospital.tv.datamodels.toBloodPressureReadings
import com.smarthospital.tv.datamodels.toHeartRateReadings
import com.smarthospital.tv.datamodels.toTemperatureReadings
import com.smarthospital.tv.ui.tvFocusDesign

@Composable
fun VitalsRow(
    vitals: List<HospitalDataModel.Vital>,
    onVitalClick: (HospitalDataModel.Vital) -> Unit = {}
) {
    TvLazyRow(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
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
            .width(360.dp)
            .height(260.dp)
            .tvFocusDesign(width = 360.dp)
            .clickable { onClick() }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}
