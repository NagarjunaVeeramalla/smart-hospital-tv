package com.smarthospital.tv.myhealth.screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import com.smarthospital.tv.myhealth.datamodels.HospitalDataModel
import com.smarthospital.tv.myhealth.datamodels.HospitalStaticData
import com.smarthospital.tv.myhealth.ui.theme.SmartHospitalAppTheme
import com.smarthospital.tv.myhealth.ui.tvFocusDesign

@Composable
fun ScheduleRow(
    activities: List<HospitalDataModel.ScheduledActivity>,
    onScheduleClick: (HospitalDataModel.ScheduledActivity) -> Unit = {}
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(activities.size) { index ->
            ScheduleCard(
                activity = activities[index],
                onClick = { onScheduleClick(activities[index]) }
            )
        }
    }
}

@Composable
fun ScheduleCard(
    activity: HospitalDataModel.ScheduledActivity,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .width(130.dp)
            .height(110.dp)
            .clickable { onClick() }
            .tvFocusDesign(width = 130.dp)
            .padding(12.dp)
    ) {
        Text(
            text = activity.title,
            color = Color.Gray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = activity.description,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = activity.scheduledDateTime,
            color = Color.Gray,
            fontSize = 10.sp
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun ScheduleCardPreview() {
    SmartHospitalAppTheme {
        ScheduleCard(
            activity = HospitalStaticData.data.scheduledActivities!!.first()
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun ScheduleRowPreview() {
    SmartHospitalAppTheme {
        ScheduleRow(
            activities = HospitalStaticData.data.scheduledActivities!!
        )
    }
}
