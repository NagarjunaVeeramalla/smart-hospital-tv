package com.smarthospital.tv.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smarthospital.tv.home.data.HomeStaticData
import com.smarthospital.tv.home.data.models.PatientResponseModel
import com.smarthospital.tv.home.data.models.PatientVideoModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class AppBottomMenu(val id: String, val title: String) {
    MyCare("My Health", "My Care"),
    Entertainment("Entertainment", "Entertainment"),
    TvSettings("Display Device", "TV Settings"),
    Learning("Education", "Learning"),
    RoomControl("Comforts", "Room Control"),
    Support("Help & Feedback", "Support")
}

data class HomeMenuItem(
    val type: AppBottomMenu,
    val badgeCount: Int = 0
) {
    val id: String get() = type.id
    val displayName: String get() = type.title
}

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

    private val _showIntroPopup = MutableStateFlow(false)
    val showIntroPopup: StateFlow<Boolean> = _showIntroPopup.asStateFlow()

    private val _showWatchLaterPopup = MutableStateFlow(false)
    val showWatchLaterPopup: StateFlow<Boolean> = _showWatchLaterPopup.asStateFlow()

    private val _introVideo = MutableStateFlow<PatientVideoModel?>(null)
    val introVideo: StateFlow<PatientVideoModel?> = _introVideo.asStateFlow()

    init {
        getHomeData()
    }

    private fun getHomeData() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            delay(1000) // Simulate network delay

            val mockData = HomeStaticData.patientData
            
            // Logic to determine if intro popup should be shown
            val overlayVideoId = mockData.config.overlayVideoId
            val overlayVideo = mockData.patientVideos.find { it.videoId == overlayVideoId }
            
            if (overlayVideo != null && !overlayVideo.isCompleted) {
                // In a real app, we might check if it was already shown in this session
                // For now, we show it if it exists and is not completed
                _introVideo.value = overlayVideo
                _showIntroPopup.value = true
            }

            // Map legacy items to new display names and add badges
            val menuItems = listOf(
                HomeMenuItem(
                    type = AppBottomMenu.MyCare,
                    badgeCount = mockData.healthNotificationCount
                ),
                HomeMenuItem(
                    type = AppBottomMenu.Entertainment
                ),
                HomeMenuItem(
                    type = AppBottomMenu.TvSettings
                ),
                HomeMenuItem(
                    type = AppBottomMenu.Learning,
                    badgeCount = mockData.assignedVideosCount
                ),
                HomeMenuItem(
                    type = AppBottomMenu.RoomControl
                ),
                HomeMenuItem(
                    type = AppBottomMenu.Support
                )
            )

            _uiState.value = HomeUiState.Success(mockData, menuItems)
        }
    }

    fun onIntroPlayClicked() {
        _showIntroPopup.value = false
        // TODO: Navigate to video player or play video
        println("HomeViewModel: Play Intro Video Clicked")
    }

    fun onIntroWatchLaterClicked() {
        _showIntroPopup.value = false
        _showWatchLaterPopup.value = true
    }

    fun onWatchLaterOkayClicked() {
        _showWatchLaterPopup.value = false
    }

    fun onIntroVideoFinished() {
        _showIntroPopup.value = false
        _showWatchLaterPopup.value = false
    }
}
