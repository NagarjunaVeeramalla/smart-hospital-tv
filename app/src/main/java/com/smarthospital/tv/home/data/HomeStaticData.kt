package com.smarthospital.tv.home.data

import com.smarthospital.tv.home.data.models.CareTeam
import com.smarthospital.tv.home.data.models.CareTeamED
import com.smarthospital.tv.home.data.models.Config
import com.smarthospital.tv.home.data.models.LocationModel
import com.smarthospital.tv.home.data.models.PatientResponseModel
import com.smarthospital.tv.home.data.models.PatientVideoModel
import com.smarthospital.tv.home.data.models.ScheduledActivity
import com.smarthospital.tv.home.data.models.ScheduledActivityGroup

object HomeStaticData {
    val patientData = PatientResponseModel(
        accountNumber = "MOCK20260119040732",
        admissionDate = "2026-01-19T16:07:32.4952204+00:00",
        assignedVideosCount = 10,
        birthDate = "1996-01-19T16:07:32.4951978+00:00",
        id = "14203-MOCK20260119040732",
        location = LocationModel(
            bed = "A",
            coid = "14203",
            displayName = "Praneeth",
            id = "",
            room = "Praneeth",
            type = "",
            unit = "664"
        ),
        config = Config(
            backgroundImageUrl = "https://yavuzceliker.github.io/sample-images/image-1022.jpg"
        ),
        medicalRecordNumber = "MOCK20260119040732",
        name = "JANE DOE",
        preferredName = "Jane",
        preferredLanguage = "English",
        careTeam = listOf(
            CareTeam(
                assignmentType = "Location",
                clinicalRole = "CTA.ClinicalRoles.RegisteredNurse",
                firstName = "Lindsay",
                lastName = "T.",
                hca34 = "CORPGENRAUL1",
                slot = "CTA.ClinicalRoles.RN.PCT"
            ),
            CareTeam(
                assignmentType = "Location",
                clinicalRole = "CTA.ClinicalRoles.RegisteredNurse",
                firstName = "Amy",
                lastName = "J.",
                hca34 = "CORPGENRAUL2",
                slot = "CTA.ClinicalRoles.RN.CRN"
            ),
            CareTeam(
                assignmentType = "Location",
                clinicalRole = "CTA.ClinicalRoles.RegisteredNurse",
                firstName = "Alex",
                lastName = "P.",
                hca34 = "CORPGENRAUL3",
                slot = "CTA.ClinicalRoles.RN.1N"
            )
        ),
        goals = listOf(
            "Patient Goal 1",
            "Patient Goal 2",
            "Patient Goal 3"
        ),
        scheduledActivities = listOf(
            ScheduledActivity(
                type = "Procedure",
                id = "14203-20260119040733",
                title = "Radiology",
                description = "Radiology Unverified",
                scheduledDateTime = "2026-01-20T00:07:33.6148343Z"
            ),
            ScheduledActivity(
                type = "Procedure",
                id = "14203-20260119040734",
                title = "CT Scan",
                description = "CT Scan Verified",
                scheduledDateTime = "2026-01-20T00:07:33.6149019Z"
            )
        ),
        patientVideos = listOf(
            PatientVideoModel(
                id = "fac3b1cb-d9af-4f1e-9d3c-aed8934cf9cd",
                fullAccountNumber = "14203-MOCK20260119040732",
                videoId = "fac3b1cb-d9af-4f1e-9d3c-aed8934cf9cd",
                title = "Hospital Overview",
                thumbnailurl = "https://vision-dev.hcahealthcare.cloud/v2/assets/education/images/TriStarHendersonville_HospitalOverview.jpg",
                length = 135000,
                progress = 0,
                category = "Additional",
                isCompleted = false
            )
        ),
        scheduledActivityGroupCounts = listOf(
            ScheduledActivityGroup("X-Ray", "0/1"),
            ScheduledActivityGroup("CT Scans", "0/1"),
            ScheduledActivityGroup("Labs", "1/1"),
            ScheduledActivityGroup("Imaging", "0/0")
        ),
        careTeamEDMembers = listOf(
            CareTeamED("Alex", "P.", "Nurse")
        ),
        showVisitMyHealth = false,
        healthNotificationCount = 2
    )
}
