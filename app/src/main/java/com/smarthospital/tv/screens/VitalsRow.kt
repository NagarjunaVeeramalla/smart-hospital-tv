package com.smarthospital.tv.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyRow
import com.smarthospital.tv.datamodels.HospitalDataModel
import com.smarthospital.tv.datamodels.toBloodPressureReadings
import com.smarthospital.tv.datamodels.toHeartRateReadings
import com.smarthospital.tv.datamodels.toTemperatureReadings

@Composable
fun VitalsRow(
    vitals: List<HospitalDataModel.Vital>
) {
    TvLazyRow(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        vitals.firstOrNull { it.displayName == "Blood Pressure" }?.let { bp ->
            item {
                PatientVitalCard {
                    BloodPressureChart(
                        readings = bp.toBloodPressureReadings()
                    )
                }
            }
        }

        vitals.firstOrNull { it.displayName == "Temperature" }?.let { temp ->
            item {
                PatientVitalCard {
                    TemperatureChart(
                        readings = temp.toTemperatureReadings()
                    )
                }
            }
        }

        vitals.firstOrNull { it.displayName == "Heart Rate" }?.let { hr ->
            item {
                PatientVitalCard {
                    HeartRateChart(
                        readings = hr.toHeartRateReadings()
                    )
                }
            }
        }
    }
}



