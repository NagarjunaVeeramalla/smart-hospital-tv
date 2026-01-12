package com.smarthospital.tv.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.material3.Text
import com.smarthospital.tv.datamodels.HospitalDataModel
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
            .width(160.dp)
            .height(160.dp)
            .tvFocusDesign(width = 160.dp, shape = RoundedCornerShape(14.dp))
            .padding(16.dp)
    ) {
        Text(text = activity.title, color = Color.White)
        Text(text = activity.description, color = Color.LightGray)
        Text(text = activity.scheduledDateTime, color = Color.Gray)
    }
}
