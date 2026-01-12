package com.smarthospital.tv.kotlinxml

//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.hcahealthcare.stb_android.data.NetworkResult
//import com.hcahealthcare.stb_android.data.Repository
//import com.hcahealthcare.stb_android.dataModels.HospitalDataModel
//import com.hcahealthcare.stb_android.util.timer.TimerCallback
//import com.hcahealthcare.stb_android.util.timer.TimerHelper
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class HospitalDashboardViewModel @Inject constructor(
//    private val repository: Repository,
//    private val timerHelper: TimerHelper
//): ViewModel(),TimerCallback {
//
//    private var _myHealthResponse = MutableLiveData<NetworkResult<HospitalDataModel>>()
//    val myHealthResponse: LiveData<NetworkResult<HospitalDataModel>> = _myHealthResponse
//
//    var accountNumber: String? = ""
//
//    init {
//        timerHelper.addCallback(this)
//    }
//
//    fun fetchHospitalDetails() {
//        viewModelScope.launch {
//            repository.getHospitalDetails(accountNumber).collect {
//                _myHealthResponse.postValue(it)
//            }
//        }
//    }
//
//    override fun onCleared() {
//        timerHelper.removeCallback(this)
//        super.onCleared()
//    }
//
//    override fun onFinished() {
//        timerHelper.cancelTimer()
//        fetchHospitalDetails()
//    }
//
//}