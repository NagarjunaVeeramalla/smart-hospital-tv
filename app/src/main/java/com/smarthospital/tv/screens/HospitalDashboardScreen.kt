package com.smarthospital.tv.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.material3.Text
import com.smarthospital.tv.datamodels.HospitalDataModel
import com.smarthospital.tv.ui.HospitalUiState
import com.smarthospital.tv.viewmodels.HospitalDashboardViewModel


@Composable
fun HospitalDashboardScreen(viewModel: HospitalDashboardViewModel = viewModel(), visionMode: String = "ED ONLY") {
    val uiState by viewModel.myHealthUiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getHospitalDashboardData()
    }
    when (uiState) {
        is HospitalUiState.Loading -> {
            LoadingContent()
        }

        is HospitalUiState.Success -> {
            val data = (uiState as HospitalUiState.Success).data
            HospitalContent(data, visionMode)
        }

        is HospitalUiState.Error -> {
            ErrorContent((uiState as HospitalUiState.Error).message)
        }
    }
}


@Composable
fun HospitalContent(data: HospitalDataModel, visionMode: String) {

    TvLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)),
        contentPadding = PaddingValues(
            start = 48.dp,
            top = 32.dp,
            end = 48.dp,
            bottom = 32.dp
        ),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        // Header
        item {
            Text(
                text = "My Health",
                color = Color.White
            )
        }

        if (visionMode == "Ed Only") {
            item {
                HospitalDashboardItem(title = "General Info") {
                    GeneralInfoRow(data.generalInfo!!)
                }
            }
            item {
                HospitalDashboardItem(title = "Orders") {
                    OrdersRow(data.scheduledActivityGroupCounts!!)
                }
            }
            item {
                HospitalDashboardItem(title = "Care Team") {
                    CareTeamRow(data.careTeamED!!)
                }
            }
        } else {
            item {
                HospitalDashboardItem(title = "Vitals") {
                    VitalsRow(vitals = data.vitalSigns!!)
                }
            }
            item {
                HospitalDashboardItem(title = "Schedule Activities") {
                    ScheduleRow(data.scheduledActivities!!)
                }
            }
            item {
                HospitalDashboardItem(title = "Care Team") {
                    CareTeamRow(data.careTeam!!) // reuse
                }
            }
        }

        item {
            HospitalDashboardItem(title = "Staff History") {
                StaffHistoryRow(data.staffHistory!!)
            }
        }
        item {
            HospitalBanner(imageUrl = "")
        }
    }
}


@Composable
fun LoadingContent() {
    Text(
        text = "Loading...",
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
}

@Composable
fun ErrorContent(message: String) {
    Text(
        text = message,
        color = Color.Red,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
}

