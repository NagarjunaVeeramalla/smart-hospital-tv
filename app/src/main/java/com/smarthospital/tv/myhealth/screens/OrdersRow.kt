package com.smarthospital.tv.myhealth.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import com.smarthospital.tv.myhealth.datamodels.HospitalDataModel
import com.smarthospital.tv.myhealth.ui.theme.SmartHospitalAppTheme
import com.smarthospital.tv.myhealth.ui.tvFocusDesign

@Composable
fun OrdersRow(
    data: List<HospitalDataModel.ScheduledActivityGroup>
) {
    LazyRow (
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(data) { item ->
            OrderCard(item)
        }
    }
}

@Composable
fun OrderCard(
    item: HospitalDataModel.ScheduledActivityGroup
) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .height(90.dp)
            .tvFocusDesign(width = 140.dp)
            .padding(12.dp)
    ) {
        Text(
            text = item.groupName,
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.orderCountRatio,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun OrdersRowPreview() {
    SmartHospitalAppTheme {
        OrdersRow(
            data = listOf(
                HospitalDataModel.ScheduledActivityGroup("X-ray", "2/3"),
                HospitalDataModel.ScheduledActivityGroup("CT Scan", "3/5"),
                HospitalDataModel.ScheduledActivityGroup("Labs", "2/3"),
                HospitalDataModel.ScheduledActivityGroup("Misc. Orders", "0/0")
            )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun OrderCardPreview() {
    SmartHospitalAppTheme {
        OrderCard(
            item = HospitalDataModel.ScheduledActivityGroup(
                groupName = "X-ray",
                orderCountRatio = "2/3"
            )
        )
    }
}
