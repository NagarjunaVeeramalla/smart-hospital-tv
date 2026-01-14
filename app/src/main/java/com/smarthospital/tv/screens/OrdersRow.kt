package com.smarthospital.tv.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.material3.Text
import com.smarthospital.tv.datamodels.HospitalDataModel
import com.smarthospital.tv.ui.theme.VisionAppTheme
import com.smarthospital.tv.ui.tvFocusDesign

@Composable
fun OrdersRow(
    data: List<HospitalDataModel.ScheduledActivityGroup>
) {
    TvLazyRow(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(data.size) { index ->
            OrderCard(data[index])
        }
    }
}

@Composable
fun OrderCard(
    item: HospitalDataModel.ScheduledActivityGroup
) {
    Column(
        modifier = Modifier
            .width(130.dp)
            .height(110.dp)
            .tvFocusDesign(width = 130.dp, shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(
            text = item.groupName,
            color = Color.Gray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.orderCountRatio,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun OrdersRowPreview() {
    VisionAppTheme {
        OrdersRow(
            data = listOf(
                HospitalDataModel.ScheduledActivityGroup("X-ray", "2/2"),
                HospitalDataModel.ScheduledActivityGroup("CT Scan", "3/5"),
                HospitalDataModel.ScheduledActivityGroup("Labs", "2/11"),
                HospitalDataModel.ScheduledActivityGroup("Misc. Orders", "0/0")
            )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun OrderCardPreview() {
    VisionAppTheme {
        OrderCard(
            item = HospitalDataModel.ScheduledActivityGroup(
                groupName = "X-ray",
                orderCountRatio = "2/2"
            )
        )
    }
}
