package com.smarthospital.tv.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smarthospital.tv.datamodels.HospitalDataModel
import com.smarthospital.tv.ui.HospitalUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HospitalDashboardViewModel : ViewModel() {

    private val _myHealthUiState =
        MutableStateFlow<HospitalUiState>(HospitalUiState.Loading)

    val myHealthUiState: StateFlow<HospitalUiState> =
        _myHealthUiState.asStateFlow()

    fun getHospitalDashboardData() {
        viewModelScope.launch {
            _myHealthUiState.value = HospitalUiState.Loading

            // Simulate network delay
            delay(1000)

            _myHealthUiState.value = HospitalUiState.Success(
                getStaticHospitalDashboardData()
            )
        }
    }

    private fun getStaticHospitalDashboardData(): HospitalDataModel {
        return HospitalDataModel(

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
                    title = "Pain Level",
                    values = listOf("7/10", "Hurts Even More"),
                    card = HospitalDataModel.Card(
                        title = "Wong-Baker Pain Scale",
                        description = "Pain rating scale used by care team.",
                        imageUrl = "https://example.com/pain_scale.png"
                    )
                )
            ),

            isFeedbackComplete = true
        )
    }
}
