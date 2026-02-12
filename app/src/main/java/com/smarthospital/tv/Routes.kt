package com.smarthospital.tv

sealed class Routes(val route: String) {
    data object Home : Routes("home")
    data object Dashboard : Routes("dashboard")
    data object VitalDetail : Routes("vital_detail")
    data object FeedbackDirectScreen : Routes("feedback")

    data object FeedbackMainScreen : Routes("main_nav_rail")
    data object PainLevelDetails : Routes("pain_level_details/{title}/{description}/{imageUrl}") {
        fun buildRoute(title: String, description: String, imageUrl: String): String {
            val encodedTitle = java.net.URLEncoder.encode(title, "UTF-8")
            val encodedDescription = java.net.URLEncoder.encode(description, "UTF-8")
            val encodedImageUrl = java.net.URLEncoder.encode(imageUrl, "UTF-8")
            return "pain_level_details/$encodedTitle/$encodedDescription/$encodedImageUrl"
        }
    }
    data object ScheduleDetails : Routes("schedule_details/{title}/{description}") {
        fun buildRoute(title: String, description: String): String {
            val encodedTitle = java.net.URLEncoder.encode(title, "UTF-8")
            val encodedDescription = java.net.URLEncoder.encode(description, "UTF-8")
            return "schedule_details/$encodedTitle/$encodedDescription"
        }
    }
}
