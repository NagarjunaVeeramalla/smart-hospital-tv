package com.smarthospital.tv.datamodels

data class HospitalDashboardData(
    val vitals: List<VitalItem>,
    val scheduledActivities: List<ScheduledActivity>,
    val careTeam: List<CareTeamMember>
)

data class VitalItem(
    val displayName: String,
    val unit: String?,
    val values: List<String>
)

data class ScheduledActivity(
    val title: String,
    val description: String,
    val time: String
)

data class CareTeamMember(
    val firstName: String,
    val lastName: String,
    val role: String,
    val id: String? = null,
    val profileImageRes: Int? = null
)
