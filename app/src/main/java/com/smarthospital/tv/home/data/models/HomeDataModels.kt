package com.smarthospital.tv.home.data.models

data class PatientResponseModel(
    val accountNumber: String,
    val admissionDate: String,
    val assignedVideosCount: Int,
    val birthDate: String?,
    val id: String,
    val location: LocationModel?,
    val config: Config,
    val medicalRecordNumber: String?,
    val name: String,
    val preferredName: String,
    val preferredLanguage: String?,
    val careTeam: List<CareTeam>,
    val goals: List<String>,
    val scheduledActivities: List<ScheduledActivity>,
    val patientVideos: List<PatientVideoModel>,
    val scheduledActivityGroupCounts: List<ScheduledActivityGroup>?,
    val careTeamEDMembers: List<CareTeamED>?,
    val showVisitMyHealth: Boolean,
    val healthNotificationCount: Int,

    ) {
    constructor() : this("", "", 0, "", "",null,
        Config(), "", "", "", "", listOf(), listOf(), listOf(),
        listOf(), listOf(), listOf(),false, 0
    )


    fun getCareProviderName(role: String): String {
        for (careProvider in careTeam) {
            if (careProvider.slot == role) {
                return careProvider.firstName
            }
        }
        return ""
    }

    fun getEdProviderName(): String {
        val attendingProvider = careTeamEDMembers.orEmpty().find { it.role == "Attending Provider" }
        if (attendingProvider != null) {
            return "Dr. ${attendingProvider.lastName}"
        }

        val residentProvider = careTeamEDMembers.orEmpty().find { it.role == "Resident Provider" }
        if (residentProvider != null) {
            return "Dr. ${residentProvider.lastName}"
        }
        return ""
    }

    fun getEdPaNpName(role: String): String {
        var paNpName = ""
        for (careProvider in careTeamEDMembers.orEmpty()) {
            if (careProvider.role == role) {
                if (role == "PA/NP")
                    paNpName = careProvider.firstName
            }
        }
        return paNpName
    }

    fun getEdNurseName(): String {
        var nurseName = ""
        var nurseLeaderName = ""

        careTeamEDMembers.orEmpty().forEach { careProvider ->
            when (careProvider.role) {
                "Nurse" -> nurseName = careProvider.firstName
                "Nurse Leader" -> nurseLeaderName = careProvider.firstName
            }
        }

        return if (nurseLeaderName.isNotEmpty() && nurseName.isNotEmpty()) {
            "$nurseLeaderName & $nurseName"
        } else if (nurseLeaderName.isNotEmpty()) {
            nurseLeaderName
        } else if (nurseName.isNotEmpty()) {
            nurseName
        } else {
            ""
        }
    }

}

data class CareTeam(
    val assignmentType: String? = null,
    val clinicalRole: String,
    val firstName: String,
    val hca34: String? = null,
    val lastName: String,
    val slot: String? = null
)

data class LocationModel(
    val bed: String,
    val coid: String,
    val displayName: String,
    val id: String,
    val room: String,
    val type: String,
    val unit: String
)

data class PatientVideoModel(
    val id: String,
    val fullAccountNumber: String,
    val videoId: String,
    val title: String,
    val thumbnailurl: String,
    val length: Int,
    val progress: Int,
    val category: String,
    val isCompleted: Boolean
)

data class ScheduledActivityGroup(
    val groupName: String,
    val orderCountRatio: String
)

data class CareTeamED(
    val firstName: String,
    val lastName: String,
    val role: String
)

data class ScheduledActivity(
    val type: String,
    val id: String,
    val title: String,
    val description: String,
    val scheduledDateTime: String
)

data class Config(
    val backgroundImageUrl: String? = null,
    val menuItems: List<String> = emptyList()
)
