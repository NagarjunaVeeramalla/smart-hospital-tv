package com.smarthospital.tv.myhealth.screens

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.material3.Text
import com.smarthospital.tv.myhealth.datamodels.HospitalDataModel
import com.smarthospital.tv.myhealth.ui.theme.SmartHospitalAppTheme
import com.smarthospital.tv.myhealth.ui.tvFocusDesign

@Composable
fun StaffHistoryRow(
    data: List<HospitalDataModel.StaffHistory>
) {
    LazyRow(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(data.size) { index ->
            MedicalStaffCard(data[index])
        }
    }
}

@Composable
fun MedicalStaffCard(
    item: HospitalDataModel.StaffHistory
) {
    Row(
        modifier = Modifier
            .width(260.dp)
            .height(86.dp)
            .tvFocusDesign(width = 260.dp)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Image Placeholder
        Image(
            painter = painterResource(
                id = R.mipmap.sym_def_app_icon ?: 0
            ),
            contentDescription = "Medical staff member",
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Text section
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.firstName,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            
            Text(
                text = item.staffType,
                color = Color.Gray,
                fontSize = 12.sp,
                maxLines = 1
            )
            
            Text(
                text = item.enteredDateTime,
                color = Color.DarkGray,
                fontSize = 10.sp,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun StaffHistoryRowPreview() {
    SmartHospitalAppTheme {
        StaffHistoryRow(
            data = listOf(
                HospitalDataModel.StaffHistory(
                    firstName = "Dr. Smith",
                    staffType = "Physician",
                    enteredDateTime = "Nov 18, 2025 at 6:30 PM"
                ),
                HospitalDataModel.StaffHistory(
                    firstName = "Nurse Jane",
                    staffType = "Nurse",
                    enteredDateTime = "Nov 18, 2025 at 8:00 PM"
                )
            )
        )
    }
}
