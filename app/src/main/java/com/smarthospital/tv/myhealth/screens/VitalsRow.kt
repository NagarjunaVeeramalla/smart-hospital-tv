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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smarthospital.tv.myhealth.datamodels.HospitalDataModel
import com.smarthospital.tv.myhealth.datamodels.HospitalStaticData
import com.smarthospital.tv.myhealth.datamodels.toBloodPressureReadings
import com.smarthospital.tv.myhealth.datamodels.toHeartRateReadings
import com.smarthospital.tv.myhealth.datamodels.toTemperatureReadings
import com.smarthospital.tv.myhealth.ui.tvFocusDesign

@Composable
fun VitalsRow(
    vitals: List<HospitalDataModel.Vital>,
    focusRequester: FocusRequester? = null,
    onVitalClick: (HospitalDataModel.Vital) -> Unit = {}
) {
    LazyRow (
        contentPadding = PaddingValues(start = 12.dp, end = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        vitals.firstOrNull { it.displayName == "Blood Pressure" }?.let { bp ->
            item {
                PatientVitalCard(
                    modifier = if (focusRequester != null) Modifier.focusRequester(focusRequester) else Modifier,
                    onClick = { onVitalClick(bp) }
                ) {
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
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
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
    VitalsRow(vitals = HospitalStaticData.data.vitalSigns!!)
}