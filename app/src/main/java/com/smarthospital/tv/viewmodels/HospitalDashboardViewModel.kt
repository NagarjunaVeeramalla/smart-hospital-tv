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

            isFeedbackComplete = true
        )
    }
}
