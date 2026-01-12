package com.smarthospital.tv.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyRow
import com.smarthospital.tv.datamodels.HospitalDataModel

@Composable
fun GeneralInfoRow(
    data: List<HospitalDataModel.GeneralInfo>
) {
    TvLazyRow(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(data.size) { index ->
            PatientInfoCard(info = data[index])
        }
    }
}
