package com.smarthospital.tv.screens


import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import com.smarthospital.tv.datamodels.HospitalDataModel
import com.smarthospital.tv.ui.tvFocusDesign

@Composable
fun CareTeamCard(
    member: HospitalDataModel.CareTeam
) {
    Row(
        modifier = Modifier
            .width(420.dp)
            .height(120.dp)
            .tvFocusDesign(width = 420.dp, shape = RoundedCornerShape(14.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Profile Image
        Image(
            painter = painterResource(
                id = R.mipmap.sym_def_app_icon ?: 0
            ),
            contentDescription = "Care team member",
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Text section
        Column(
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "${member.firstName} ${member.lastName}",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = member.clinicalRole!!,
                color = Color.LightGray,
                fontSize = 14.sp
            )

            member.hca34?.let {
                Text(
                    text = it,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}

