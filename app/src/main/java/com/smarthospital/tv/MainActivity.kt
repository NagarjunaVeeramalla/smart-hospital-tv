package com.smarthospital.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import com.smarthospital.tv.feedback.screens.FeedbackDirectScreen
import com.smarthospital.tv.feedback.screens.MainNavRailScreen
import com.smarthospital.tv.myhealth.screens.HospitalDashboardScreen
import com.smarthospital.tv.myhealth.ui.theme.SmartHospitalAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartHospitalAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RectangleShape
                ) {
                    //MainNavRailScreen()
                    //FeedbackDirectScreen()
                    HospitalDashboardScreen()
                }
            }
        }
    }
}