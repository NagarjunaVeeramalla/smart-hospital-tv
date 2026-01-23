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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.smarthospital.tv.feedback.screens.ShareFeedbackDialog
import com.smarthospital.tv.myhealth.ui.DashboardMode
import com.smarthospital.tv.myhealth.ui.HospitalUiState
import com.smarthospital.tv.myhealth.ui.theme.SmartHospitalAppTheme
import com.smarthospital.tv.myhealth.viewmodels.HospitalDashboardViewModel


@Composable
fun HospitalDashboardScreen(
    viewModel: HospitalDashboardViewModel = viewModel(),
    dashboardMode: DashboardMode = DashboardMode.EMERGENCY_DEPARTMENT
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
            HospitalContent(data, dashboardMode)
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
                onShareFeedbackClick = {
                    // For now, we just close the invitation. 
                    // In a real app, this might navigate to a full feedback screen.
                    hasUserDismissedFeedbackPopup = true
                },
                onNotNowClick = {
                    hasUserDismissedFeedbackPopup = true
                }
            )
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

        //if (dashboardMode == DashboardMode.EMERGENCY_DEPARTMENT) {
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
        //} else {
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
        //}

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

@Preview(device = "id:tv_1080p")
@Composable
fun HospitalContentPreview() {
    val data = HospitalDataModel(

        vitalSigns = listOf(

            // ❤️ HEART RATE
            HospitalDataModel.Vital(
                displayName = "Heart Rate",
                hasData = true,
                compoundSeparator = "",
                unit = "BPM",
                cardTitle = "Understanding Your Heart Rate",
                cardDescription = "Normal heart rate is 60–100 BPM.",
                measurements = listOf(
                    HospitalDataModel.Measurement(
                        captureDateTime = "2025-09-25T08:35:17Z",
                        values = listOf(72.0)
                    ),
                    HospitalDataModel.Measurement(
                        captureDateTime = "2025-09-25T14:20:10Z",
                        values = listOf(78.0)
                    ),
                    HospitalDataModel.Measurement(
                        captureDateTime = "2025-09-25T20:35:17Z",
                        values = listOf(79.0)
                    )
                )
            ),

            // 🩸 BLOOD PRESSURE
            HospitalDataModel.Vital(
                displayName = "Blood Pressure",
                hasData = true,
                compoundSeparator = "/",
                unit = "mmHg",
                cardTitle = "Understanding Your Blood Pressure",
                cardDescription = "Normal BP is less than 120/80.",
                measurements = listOf(
                    HospitalDataModel.Measurement(
                        captureDateTime = "2025-09-25T08:30:00Z",
                        values = listOf(135.0, 88.0)
                    ),
                    HospitalDataModel.Measurement(
                        captureDateTime = "2025-09-25T13:15:00Z",
                        values = listOf(140.0, 92.0)
                    ),
                    HospitalDataModel.Measurement(
                        captureDateTime = "2025-09-25T20:35:17Z",
                        values = listOf(138.0, 90.0)
                    )
                )
            ),

            // 🌡 TEMPERATURE
            HospitalDataModel.Vital(
                displayName = "Temperature",
                hasData = true,
                compoundSeparator = ",",
                unit = "°F",
                cardTitle = "Understanding Your Body Temperature",
                cardDescription = "Normal body temperature is around 98.6°F.",
                measurements = listOf(
                    HospitalDataModel.Measurement(
                        captureDateTime = "2025-09-25T08:25:00Z",
                        values = listOf(98.4)
                    ),
                    HospitalDataModel.Measurement(
                        captureDateTime = "2025-09-25T13:10:00Z",
                        values = listOf(99.1)
                    ),
                    HospitalDataModel.Measurement(
                        captureDateTime = "2025-09-25T20:30:00Z",
                        values = listOf(101.2)
                    )
                )
            )
        ),

        careTeam = listOf(
            HospitalDataModel.CareTeam(
                hca34 = "LID7031",
                firstName = "Amanda",
                lastName = "R.",
                slot = "CTA.ClinicalRoles.RN",
                clinicalRole = "Registered Nurse",
                assignmentType = "Location"
            )
        ),

        scheduledActivities = listOf(
            HospitalDataModel.ScheduledActivity(
                type = "Procedure",
                id = "14203-20250925033517",
                title = "CT Scan",
                description = "CT Scan Transmitted",
                scheduledDateTime = "2025-09-26T04:35:17Z"
            )
        ),

        scheduledActivityGroupCounts = listOf(
            HospitalDataModel.ScheduledActivityGroup(
                groupName = "CT Scans",
                orderCountRatio = "0/1"
            )
        ),

        staffHistory = listOf(
            HospitalDataModel.StaffHistory(
                enteredDateTime = "2025-09-25T08:35:17Z",
                firstName = "Amanda",
                staffType = "Registered Nurse"
            ),
            HospitalDataModel.StaffHistory(
                enteredDateTime = "2025-09-25T08:35:17Z",
                firstName = "Amanda",
                staffType = "Registered Nurse"
            )
        ),

        careTeamED = listOf(
            HospitalDataModel.CareTeam(
                hca34 = null,
                firstName = "Lunese",
                lastName = "M.",
                slot = null,
                clinicalRole = "Nurse",
                assignmentType = null
            )
        ),

        generalInfo = listOf(
            HospitalDataModel.GeneralInfo(
                title = "Current Pain Rating",
                values = listOf(
                    "4/10",
                    "https://farm9.staticflickr.com/8295/8007075227_dc958c1fe6_z_d.jpg",
                    "Manageable Discomfort"
                ),
                card = HospitalDataModel.Card(
                    title = "Understanding the Pain Assessment Tool",
                    description = "Our healthcare professionals employ the Wong-Baker scale to evaluate your comfort levels. Sharing an accurate rating helps us customize your pain management and therapeutic approach effectively.",
                    imageUrl = "https://farm2.staticflickr.com/1449/24800673529_64272a66ec_z_d.jpg"
                )
            ),
            HospitalDataModel.GeneralInfo(
                title = "Entry Details",
                values = listOf("Admitted on Nov 18, 2025 at 6:27 PM"),
                card = null
            ),
            HospitalDataModel.GeneralInfo(
                title = "Dietary Guidelines",
                values = listOf(
                    "Restricted: NPO (Nothing by mouth)",
                    "https://farm8.staticflickr.com/7377/9359257263_81b080a039_z_d.jpg"
                ),
                card = null
            )
        ),

        isFeedbackComplete = false
    )
    SmartHospitalAppTheme {
        HospitalContent(data = data, dashboardMode = DashboardMode.EMERGENCY_DEPARTMENT)
    }
}
