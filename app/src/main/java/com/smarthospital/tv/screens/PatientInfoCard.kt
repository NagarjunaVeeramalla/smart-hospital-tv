package com.smarthospital.tv.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Text
import com.smarthospital.tv.datamodels.HospitalDataModel
import com.smarthospital.tv.ui.tvFocusDesign

@Composable
fun PatientInfoCard(
    info: HospitalDataModel.GeneralInfo
) {
    Column(
        modifier = Modifier
            .width(360.dp)
            .height(200.dp)
            .tvFocusDesign(width = 360.dp)
            .padding(16.dp)
    ) {
        Text(text = info.title, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = info.values?.getOrNull(0).orEmpty(),
            color = Color.LightGray
        )
    }
}
