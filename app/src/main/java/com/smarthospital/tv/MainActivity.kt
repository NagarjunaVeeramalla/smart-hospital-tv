package com.smarthospital.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import com.smarthospital.tv.feedback.screens.FeedbackDirectScreen
import com.smarthospital.tv.feedback.screens.MainNavRailScreen
import com.smarthospital.tv.myhealth.datamodels.HospitalDataModel
import com.smarthospital.tv.myhealth.screens.HospitalDashboardScreen
import com.smarthospital.tv.myhealth.screens.VitalDetailScreen
import com.smarthospital.tv.myhealth.ui.theme.SmartHospitalAppTheme
import com.smarthospital.tv.myhealth.viewmodels.HospitalDashboardViewModel

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
                    val navController = rememberNavController()
                    val viewModel: HospitalDashboardViewModel = viewModel()

                    NavHost(navController = navController, startDestination = "dashboard") {
                        composable("dashboard") {
                            HospitalDashboardScreen(
                                viewModel = viewModel,
                                onVitalClick = { vital ->
                                    navController.currentBackStackEntry?.savedStateHandle?.set("vital", vital)
                                    navController.navigate("vital_detail")
                                },
                                onFeedbackClick = {
                                    navController.navigate("feedback")
                                }
                            )
                        }
                        composable(
                            route = "vital_detail"
                        ) { _ ->
                            val vital = navController.previousBackStackEntry?.savedStateHandle?.get<HospitalDataModel.Vital>("vital")
                            VitalDetailScreen(
                                vital = vital,
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable("feedback") {
                            FeedbackDirectScreen(
                                onFeedbackComplete = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}