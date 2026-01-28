package com.smarthospital.tv.myhealth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.focus.FocusRequester
import com.smarthospital.tv.feedback.screens.ShareFeedbackDialog
import com.smarthospital.tv.myhealth.datamodels.HospitalStaticData
import com.smarthospital.tv.myhealth.ui.DashboardMode
import com.smarthospital.tv.myhealth.ui.HospitalUiState
import com.smarthospital.tv.myhealth.ui.theme.SmartHospitalAppTheme
import com.smarthospital.tv.myhealth.viewmodels.HospitalDashboardViewModel


@Composable
fun HospitalDashboardScreen(
    viewModel: HospitalDashboardViewModel = viewModel(),
    dashboardMode: DashboardMode = DashboardMode.INPATIENT_WARD,
    onVitalClick: (HospitalDataModel.Vital) -> Unit = {},
    onFeedbackClick: () -> Unit = {}
) {
    val uiState by viewModel.myHealthUiState.collectAsState()
    var hasUserDismissedFeedbackPopup by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getHospitalDashboardData()
    }

    val showFeedbackDialog = (uiState as? HospitalUiState.Success)?.data?.let {
        !it.isFeedbackComplete && !hasUserDismissedFeedbackPopup
    } ?: false

    when (uiState) {
        is HospitalUiState.Loading -> {
            LoadingContent()
        }

        is HospitalUiState.Success -> {
            val data = (uiState as HospitalUiState.Success).data
            HospitalContent(data, dashboardMode, onVitalClick)
        }

        is HospitalUiState.Error -> {
            ErrorContent((uiState as HospitalUiState.Error).message)
        }
    }
    if (showFeedbackDialog) {
        Dialog(
            onDismissRequest = { hasUserDismissedFeedbackPopup = true },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            ShareFeedbackDialog(
                modifier = Modifier.padding(50.dp),
                onShareFeedbackClick = {
                    hasUserDismissedFeedbackPopup = true
                    onFeedbackClick()
                },
                onNotNowClick = {
                    hasUserDismissedFeedbackPopup = true
                }
            )
        }
    }
}


@Composable
fun HospitalContent(
    data: HospitalDataModel,
    dashboardMode: DashboardMode,
    onVitalClick: (HospitalDataModel.Vital) -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)),
        contentPadding = PaddingValues(
            start = 28.dp,
            top = 32.dp,
            end = 28.dp,
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
                        focusRequester = focusRequester,
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
                        focusRequester = focusRequester,
                        onVitalClick = onVitalClick
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
            HospitalBanner(imageUrl = "https://yavuzceliker.github.io/sample-images/image-1022.jpg")
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

@Preview(device = "id:tv_1080p")
@Composable
fun HospitalContentPreview() {
    val data = HospitalStaticData.data
    SmartHospitalAppTheme {
        HospitalContent(data = data, dashboardMode = DashboardMode.INPATIENT_WARD)
    }
}

