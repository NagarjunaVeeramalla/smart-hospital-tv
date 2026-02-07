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
import androidx.compose.runtime.collectAsState
import androidx.tv.material3.Surface
import com.smarthospital.tv.feedback.screens.FeedbackDirectScreen
import com.smarthospital.tv.home.screens.HomeComposable
import com.smarthospital.tv.myhealth.screens.HospitalDashboardScreen
import com.smarthospital.tv.myhealth.screens.PainRatingScreen
import com.smarthospital.tv.myhealth.screens.ScheduleDetailsScreen
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

                    NavHost(navController = navController, startDestination = Routes.Home.route) {
                        composable(Routes.Home.route) {
                             HomeComposable(
                                 onMyHealthClick = {
                                     navController.navigate(Routes.Dashboard.route)
                                 }
                             )
                        }
                        composable(Routes.Dashboard.route) {
                            HospitalDashboardScreen(
                                viewModel = viewModel,
                                onVitalClick = { vital ->
                                    viewModel.selectVital(vital)
                                    navController.navigate(Routes.VitalDetail.route)
                                },
                                onFeedbackClick = {
                                    navController.navigate(Routes.Feedback.route)
                                },
                                onInfoClick = { info ->
                                    if (info.card != null) {
                                        navController.navigate(
                                            Routes.PainLevelDetails.buildRoute(
                                                title = info.card.title,
                                                description = info.card.description,
                                                imageUrl = info.card.imageUrl
                                            )
                                        )
                                    }
                                },
                                onScheduleClick = { activity ->
                                    navController.navigate(
                                        Routes.ScheduleDetails.buildRoute(
                                            title = activity.title,
                                            description = activity.description
                                        )
                                    )
                                }
                            )
                        }
                        composable(
                            route = Routes.VitalDetail.route
                        ) {
                            val vital = viewModel.selectedVital.collectAsState().value
                            VitalDetailScreen(
                                vital = vital,
                                onBackClick = {
                                    navController.popBackStack()
                                    viewModel.selectVital(null)
                                }
                            )
                        }
                        composable(
                            route = Routes.PainLevelDetails.route,
                            arguments = listOf(
                                navArgument("title") { type = androidx.navigation.NavType.StringType },
                                navArgument("description") { type = androidx.navigation.NavType.StringType },
                                navArgument("imageUrl") { type = androidx.navigation.NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val title = backStackEntry.arguments?.getString("title")?.let { java.net.URLDecoder.decode(it, "UTF-8") } ?: ""
                            val description = backStackEntry.arguments?.getString("description")?.let { java.net.URLDecoder.decode(it, "UTF-8") } ?: ""
                            val imageUrl = backStackEntry.arguments?.getString("imageUrl")?.let { java.net.URLDecoder.decode(it, "UTF-8") } ?: ""

                            PainRatingScreen(
                                imageUrl = imageUrl,
                                title = title,
                                description = description,
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(
                            route = Routes.ScheduleDetails.route,
                            arguments = listOf(
                                navArgument("title") { type = androidx.navigation.NavType.StringType },
                                navArgument("description") { type = androidx.navigation.NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val title = backStackEntry.arguments?.getString("title")?.let { java.net.URLDecoder.decode(it, "UTF-8") } ?: ""
                            val description = backStackEntry.arguments?.getString("description")?.let { java.net.URLDecoder.decode(it, "UTF-8") } ?: ""

                            ScheduleDetailsScreen(
                                title = title,
                                description = description,
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(Routes.Feedback.route) {
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
