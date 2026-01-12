package com.smarthospital.tv.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyRow
import com.smarthospital.tv.datamodels.HospitalDataModel

@Composable
fun CareTeamRow(
    members: List<HospitalDataModel.CareTeam>
) {
    TvLazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(members.size) { index ->
            CareTeamCard(members[index])
        }
    }
}
