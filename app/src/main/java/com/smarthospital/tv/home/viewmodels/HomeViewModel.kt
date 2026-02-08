package com.smarthospital.tv.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smarthospital.tv.home.data.models.CareTeam
import com.smarthospital.tv.home.data.models.CareTeamED
import com.smarthospital.tv.home.data.models.Config
import com.smarthospital.tv.home.data.models.LocationModel
import com.smarthospital.tv.home.data.models.PatientResponseModel
import com.smarthospital.tv.home.data.models.PatientVideoModel
import com.smarthospital.tv.home.data.models.ScheduledActivity
import com.smarthospital.tv.home.data.models.ScheduledActivityGroup
import com.smarthospital.tv.home.data.HomeStaticData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeMenuItem(
    val id: String,
    val displayName: String,
    val badgeCount: Int = 0
)

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(
        val patientData: PatientResponseModel,
        val menuItems: List<HomeMenuItem>
    ) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getHomeData()
    }

    private fun getHomeData() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            delay(1000) // Simulate network delay

            val mockData = HomeStaticData.patientData
            
            // Map legacy items to new display names and add badges
            val menuItems = listOf(
                HomeMenuItem(
                    id = "My Health",
                    displayName = "My Care",
                    badgeCount = mockData.healthNotificationCount
                ),
                HomeMenuItem(
                    id = "Entertainment",
                    displayName = "Entertainment"
                ),
                HomeMenuItem(
                    id = "Display Device",
                    displayName = "TV Settings"
                ),
                HomeMenuItem(
                    id = "Education",
                    displayName = "Learning",
                    badgeCount = mockData.assignedVideosCount
                ),
                HomeMenuItem(
                    id = "Comforts",
                    displayName = "Room Control"
                ),
                HomeMenuItem(
                    id = "Help & Feedback",
                    displayName = "Support"
                )
            )

            _uiState.value = HomeUiState.Success(mockData, menuItems)
        }
    }
}
