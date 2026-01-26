package com.smarthospital.tv.myhealth.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Text
import com.smarthospital.tv.myhealth.ui.theme.SmartHospitalAppTheme

@Composable
fun HospitalDashboardItem(
    title: String?,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        if (!title.isNullOrBlank()) {
            Text(
                text = title,
                color = Color.LightGray,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }
        content()
    }
}

@Preview
@Composable
fun HospitalDashboardItemPreview() {
    SmartHospitalAppTheme {
        HospitalDashboardItem(
            title = "Patient Information"
        ) {
            Text(text = "This is sample content")
        }
    }
}
