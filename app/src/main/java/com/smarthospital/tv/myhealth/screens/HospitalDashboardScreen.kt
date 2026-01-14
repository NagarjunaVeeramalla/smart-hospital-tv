package com.smarthospital.tv.myhealth.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.tv.material3.Text
import com.smarthospital.tv.myhealth.datamodels.HospitalDataModel
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Alignment
import com.smarthospital.tv.feedback.screens.FeedbackDirectScreen
import com.smarthospital.tv.myhealth.ui.DashboardMode
import com.smarthospital.tv.myhealth.ui.HospitalUiState
import com.smarthospital.tv.myhealth.viewmodels.HospitalDashboardViewModel


@Composable
fun HospitalDashboardScreen(
    viewModel: HospitalDashboardViewModel = viewModel(),
    dashboardMode: DashboardMode = DashboardMode.EMERGENCY_DEPARTMENT
) {
    val uiState by viewModel.myHealthUiState.collectAsState()
    var isToShowFeedBackPopup by remember { mutableStateOf(false) }
    var hasShownFeedbackPopup by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getHospitalDashboardData()
    }

    LaunchedEffect(uiState) {
        if (uiState is HospitalUiState.Success && !hasShownFeedbackPopup) {
            val data = (uiState as HospitalUiState.Success).data
            if (!data.isFeedbackComplete) {
                isToShowFeedBackPopup = true
                hasShownFeedbackPopup = true
            }
        }
    }

    when (uiState) {
        is HospitalUiState.Loading -> {
            LoadingContent()
        }

        is HospitalUiState.Success -> {
            val data = (uiState as HospitalUiState.Success).data
            HospitalContent(data, dashboardMode)
        }

        is HospitalUiState.Error -> {
            ErrorContent((uiState as HospitalUiState.Error).message)
        }
    }
    if (isToShowFeedBackPopup) {
        Dialog(
            onDismissRequest = { isToShowFeedBackPopup = false },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = Modifier
                    .width(800.dp)
                    .height(600.dp)
                    .padding(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF2C2C2C)),
                contentAlignment = Alignment.Center
            ) {
                FeedbackDirectScreen(
                    modifier = Modifier.fillMaxSize(),
                    onFeedbackComplete = { isToShowFeedBackPopup = false }
                )
            }
        }
    }
}


@Composable
fun HospitalContent(data: HospitalDataModel, dashboardMode: DashboardMode) {

    LazyColumn(
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

        if (dashboardMode == DashboardMode.EMERGENCY_DEPARTMENT) {
            item {
                HospitalDashboardItem(title = "General Info") {
                    GeneralInfoRow(
                        data = data.generalInfo!!,
                        onInfoClick = { /* Handle info click */ }
                    )
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
                    VitalsRow(
                        vitals = data.vitalSigns!!,
                        onVitalClick = { /* Handle vital click */ }
                    )
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

