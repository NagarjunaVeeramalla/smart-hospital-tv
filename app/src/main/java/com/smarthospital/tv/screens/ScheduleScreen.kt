package com.smarthospital.tv.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
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
fun ScheduleRow(
    activities: List<HospitalDataModel.ScheduledActivity>
) {
    TvLazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(activities.size) { index ->
            ScheduleCard(activities[index])
        }
    }
}

@Composable
fun ScheduleCard(
    activity: HospitalDataModel.ScheduledActivity
) {
    Column(
        modifier = Modifier
            .width(130.dp)
            .height(110.dp)
            .tvFocusDesign(width = 130.dp, shape = RoundedCornerShape(12.dp))
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
    VisionAppTheme {
        ScheduleCard(
            activity = HospitalDataModel.ScheduledActivity(
                type = "Test",
                id = "1",
                title = "CT Scan",
                description = "Chest CT",
                scheduledDateTime = "10:30 AM"
            )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun ScheduleRowPreview() {
    VisionAppTheme {
        ScheduleRow(
            activities = listOf(
                HospitalDataModel.ScheduledActivity("Test", "1", "CT Scan", "Chest CT", "10:30 AM"),
                HospitalDataModel.ScheduledActivity("Test", "2", "X-Ray", "Left Leg", "11:00 AM"),
                HospitalDataModel.ScheduledActivity("Test", "3", "Labs", "Blood Work", "11:30 AM")
            )
        )
    }
}
