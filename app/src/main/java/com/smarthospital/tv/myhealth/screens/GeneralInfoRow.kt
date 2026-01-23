package com.smarthospital.tv.myhealth.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import com.smarthospital.tv.R
import com.smarthospital.tv.myhealth.datamodels.HospitalDataModel
import com.smarthospital.tv.myhealth.ui.theme.SmartHospitalAppTheme
import com.smarthospital.tv.myhealth.ui.tvFocusDesign

@Composable
fun GeneralInfoRow(
    data: List<HospitalDataModel.GeneralInfo>,
    onInfoClick: (HospitalDataModel.GeneralInfo) -> Unit = {}
) {
    LazyRow (
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
        "Pain Level" -> 300.dp
        "Entry Details" -> 200.dp
        else -> 360.dp
    }

    if (info.title == "Current Pain Rating" || info.title == "Pain Level") {
        Row(
            modifier = Modifier
                .width(cardWidth)
                .height(140.dp)
                .tvFocusDesign(width = cardWidth)
                .clickable { onClick() }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_sentiment_satisfied),
                    contentDescription = "Pain scale face",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = info.title,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = info.values?.getOrNull(0) ?: "--",
                    color = Color.White,
                    fontSize = 22.sp,
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
                .tvFocusDesign(width = cardWidth)
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
    SmartHospitalAppTheme {
        PatientInfoCard(
            info = HospitalDataModel.GeneralInfo(
                title = "Pain Level",
                values = listOf("0/10", "url", "No Pain"),
                card = null
            )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun EntryDetailsCardPreview() {
    SmartHospitalAppTheme {
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
    SmartHospitalAppTheme {
        PatientInfoCard(
            info = HospitalDataModel.GeneralInfo(
                title = "Dietary Guidelines",
                values = listOf("Restricted: NPO (Nothing by mouth)"),
                card = null
            )
        )
    }
}
