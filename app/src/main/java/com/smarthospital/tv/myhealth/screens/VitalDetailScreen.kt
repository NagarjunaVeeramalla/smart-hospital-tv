package com.smarthospital.tv.myhealth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import com.smarthospital.tv.myhealth.datamodels.HospitalDataModel
import com.smarthospital.tv.myhealth.datamodels.HospitalStaticData
import com.smarthospital.tv.myhealth.datamodels.toBloodPressureReadings
import com.smarthospital.tv.myhealth.datamodels.toHeartRateReadings
import com.smarthospital.tv.myhealth.datamodels.toTemperatureReadings
import com.smarthospital.tv.myhealth.ui.HospitalUiState
import com.smarthospital.tv.myhealth.viewmodels.HospitalDashboardViewModel
import com.smarthospital.tv.myhealth.ui.tvFocusDesign

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun VitalDetailScreen(
    vitalName: String,
    viewModel: HospitalDashboardViewModel,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.myHealthUiState.collectAsState()
    val data = (uiState as? HospitalUiState.Success)?.data
    val vital = data?.vitalSigns?.firstOrNull { it.displayName == vitalName }

    VitalDetailContent(
        vitalName = vitalName,
        vital = vital,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun VitalDetailContent(
    vitalName: String,
    vital: HospitalDataModel.Vital?,
    onBackClick: () -> Unit = {}
) {
    // Hardcoded Texts
    val title: String
    val description: String

    when (vitalName) {
        "Blood Pressure" -> {
            title = "Understanding Your Blood Pressure"
            description = "Blood pressure is the force of blood pushing against the walls of the arteries as your heart pumps blood. Systolic (the top number) is your blood pressure when your heart beats while pumping blood. Diastolic (the bottom number) is your blood pressure when the heart is at rest between beats. A normal reading is considered less than 120/80 mmHg. Please ask your doctor or nurse for questions regarding your vital signs."
        }
        "Temperature" -> {
            title = "Understanding Your Body Temperature"
            description = "Normal body temperature varies by person, age, activity, and time of day. The average normal body temperature is generally accepted as 98.6°F. Some studies have shown that the \"normal\" body temperature can have a wide range, from 97°F to 99°F. A temperature over 100.4°F most often means you have a fever caused by an infection or illness. Please ask your doctor or nurse for questions regarding your vital signs."
        }
        "Heart Rate" -> {
            title = "Understanding Your Heart Rate"
            description = "Heart rate, also known as pulse, is the number of times a person's heart beats per minute. A normal range for adults is 60 to 100 beats per minute. A normal heart rate depends on the individual, age, body size, heart conditions, whether the person is sitting or moving, medication use and even emotions. Please ask your doctor or nurse for questions regarding your vital signs."
        }
        else -> {
            title = "Vital Details"
            description = "Details unavailable."
        }
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(50.dp), // Check padding from screenshots, seems generous
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Left Side: Text and Back Button
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(end = 40.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            
            Text(
                text = title,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = description,
                color = Color.White,
                fontSize = 16.sp,
                lineHeight = 24.sp
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Back Button
            Button(
                onClick = onBackClick,
                modifier = Modifier.tvFocusDesign(220.dp)
            ) {
                Text("Return to previous screen")
            }
            
             Spacer(modifier = Modifier.height(40.dp))
        }

        // Right Side: Chart
        Box(
            modifier = Modifier
                .width(400.dp) // Adjust based on visual
                .height(350.dp)
                // .background(Color(0xFF1E1E1E), shape = RoundedCornerShape(12.dp)) // Optional card look?
                .align(Alignment.CenterVertically)
        ) {
            if (vital != null) {
                when (vitalName) {
                    "Blood Pressure" -> {
                       if (vital.measurements != null) {
                            val readings = vital.toBloodPressureReadings()
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                BloodPressureChart(readings)
                            }
                       }
                    }
                    "Temperature" -> {
                         if (vital.measurements != null) {
                            val readings = vital.toTemperatureReadings()
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                TemperatureChart(readings)
                            }
                         }
                    }
                    "Heart Rate" -> {
                         if (vital.measurements != null) {
                            val readings = vital.toHeartRateReadings()
                             Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                HeartRateChart(readings)
                            }
                         }
                    }
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(device = "id:tv_1080p")
@Composable
fun VitalDetailScreenPreviewBP() {
    val vital = HospitalStaticData.data.vitalSigns?.firstOrNull { it.displayName == "Blood Pressure" }
    VitalDetailContent(
        vitalName = "Blood Pressure",
        vital = vital
    )
}

@androidx.compose.ui.tooling.preview.Preview(device = "id:tv_1080p")
@Composable
fun VitalDetailScreenPreviewTemp() {
    val vital = HospitalStaticData.data.vitalSigns?.firstOrNull { it.displayName == "Temperature" }
    VitalDetailContent(
        vitalName = "Temperature",
        vital = vital
    )
}

@androidx.compose.ui.tooling.preview.Preview(device = "id:tv_1080p")
@Composable
fun VitalDetailScreenPreviewHR() {
    val vital = HospitalStaticData.data.vitalSigns?.firstOrNull { it.displayName == "Heart Rate" }
    VitalDetailContent(
        vitalName = "Heart Rate",
        vital = vital
    )
}
