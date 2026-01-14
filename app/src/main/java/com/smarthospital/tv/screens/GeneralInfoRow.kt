package com.smarthospital.tv.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.material3.Text
import com.smarthospital.tv.R
import com.smarthospital.tv.datamodels.HospitalDataModel
import com.smarthospital.tv.ui.theme.VisionAppTheme
import com.smarthospital.tv.ui.tvFocusDesign

@Composable
fun GeneralInfoRow(
    data: List<HospitalDataModel.GeneralInfo>,
    onInfoClick: (HospitalDataModel.GeneralInfo) -> Unit = {}
) {
    TvLazyRow(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(data.size) { index ->
            PatientInfoCard(
                info = data[index],
                onClick = { onInfoClick(data[index]) }
            )
        }
    }
}

@Composable
fun PatientInfoCard(
    info: HospitalDataModel.GeneralInfo,
    onClick: () -> Unit = {}
) {
    val cardWidth = when (info.title) {
        "Pain Status" -> 300.dp
        "Entry Details" -> 200.dp
        else -> 360.dp
    }

    if (info.title == "Current Pain Rating" || info.title == "Pain Status") {
        Row(
            modifier = Modifier
                .width(cardWidth)
                .height(140.dp)
                .tvFocusDesign(width = cardWidth, shape = RoundedCornerShape(12.dp))
                .clickable { onClick() }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White, RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.mipmap.ic_launcher),
                    contentDescription = "Pain scale face",
                    modifier = Modifier.size(80.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = info.title,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = info.values?.getOrNull(0) ?: "--",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = info.values?.getOrNull(2) ?: "",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    lineHeight = 14.sp
                )
            }
        }
    } else {
        Column(
            modifier = Modifier
                .width(cardWidth)
                .height(140.dp)
                .tvFocusDesign(width = cardWidth, shape = RoundedCornerShape(12.dp))
                .clickable { onClick() }
                .padding(16.dp)
        ) {
            Text(
                text = info.title,
                color = Color.Gray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(12.dp))
            info.values?.forEach { valStr ->
                Text(
                    text = valStr,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun PainLevelCardPreview() {
    VisionAppTheme {
        PatientInfoCard(
            info = HospitalDataModel.GeneralInfo(
                title = "Pain Status",
                values = listOf("7/10", "url", "Hurts Even More"),
                card = null
            )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun EntryDetailsCardPreview() {
    VisionAppTheme {
        PatientInfoCard(
            info = HospitalDataModel.GeneralInfo(
                title = "Entry Details",
                values = listOf("Admitted on Nov 18, 2025 at 6:27 PM"),
                card = null
            )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun DietCardPreview() {
    VisionAppTheme {
        PatientInfoCard(
            info = HospitalDataModel.GeneralInfo(
                title = "Dietary Guidelines",
                values = listOf("Restricted: NPO (Nothing by mouth)"),
                card = null
            )
        )
    }
}
