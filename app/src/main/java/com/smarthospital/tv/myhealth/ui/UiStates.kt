package com.smarthospital.tv.myhealth.ui

import com.smarthospital.tv.myhealth.datamodels.HospitalDataModel

sealed interface HospitalUiState {
    object Loading : HospitalUiState
    data class Success(val data: HospitalDataModel) : HospitalUiState
    data class Error(val message: String) : HospitalUiState
}
