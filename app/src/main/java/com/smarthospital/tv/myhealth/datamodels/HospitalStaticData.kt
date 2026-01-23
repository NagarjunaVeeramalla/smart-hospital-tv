package com.smarthospital.tv.myhealth.datamodels

import com.smarthospital.tv.R


object HospitalStaticData {

    val data = HospitalDashboardData(
        vitals = listOf(
            VitalItem(
                displayName = "Heart Rate", unit = "BPM", values = listOf("79")
            ), VitalItem(
                displayName = "Temperature", unit = "°F / °C", values = listOf("101°F", "38.3°C")
            ), VitalItem(
                displayName = "Blood Pressure", unit = "mmHg", values = listOf("138 / 90")
            )
        ), scheduledActivities = listOf(
            ScheduledActivity(
                title = "US.RAD", description = "US.RAD L", time = "04:35 AM"
            ), ScheduledActivity(
                title = "CT Scan", description = "CT Scan Transmitted", time = "04:35 AM"
            )
        ), careTeam = listOf(
            CareTeamMember(
                "James",
                "Sorensen",
                "Admitting Physician",
                id = "#00001",
                profileImageRes = R.mipmap.ic_launcher
            ),
            CareTeamMember(
                "Margaret",
                "H.",
                "Case Management",
                id = "#00002",
                profileImageRes = R.mipmap.ic_launcher
            ),
            CareTeamMember(
                "Ellinor",
                "D.",
                "Patient Care Tech",
                id = "#00003",
                profileImageRes = R.mipmap.ic_launcher
            ),
            CareTeamMember(
                "Alexandria",
                "H.",
                "Physical Therapy",
                id = "#00004",
                profileImageRes = R.mipmap.ic_launcher
            )
        ),
        generalInfo = listOf(
            HospitalDataModel.GeneralInfo(
                title = "Pain Level",
                values = listOf(
                    "0/10",
                    "https://farm9.staticflickr.com/8295/8007075227_dc958c1fe6_z_d.jpg",
                    "No Pain"
                ),
                card = HospitalDataModel.Card(
                    title = "Understanding the Pain Assessment Tool",
                    description = "Our healthcare professionals employ the Wong-Baker scale to evaluate your comfort levels. Sharing an accurate rating helps us customize your pain management and therapeutic approach effectively.",
                    imageUrl = "https://farm2.staticflickr.com/1449/24800673529_64272a66ec_z_d.jpg"
                )
            ),
            HospitalDataModel.GeneralInfo(
                title = "Entry Details",
                values = listOf("Admitted on Nov 18, 2025 at 6:27 PM"),
                card = null
            ),
            HospitalDataModel.GeneralInfo(
                title = "Dietary Guidelines",
                values = listOf(
                    "Restricted: NPO (Nothing by mouth)",
                    "https://farm8.staticflickr.com/7377/9359257263_81b080a039_z_d.jpg"
                ),
                card = null
            )
        )
    )
}

fun Pair<Double, Double>.toTemperatureReadings(): List<TemperatureReading> {
    return listOf(
        TemperatureReading(
            time = "12:38 PM", tempF = first
        ), TemperatureReading(
            time = "6:45 AM", tempF = second
        ), TemperatureReading(
            time = "6:45 AM", tempF = second
        )
    )
}

fun Pair<Pair<Int, Int>, Pair<Int, Int>>.toBloodPressureReadings(): List<BloodPressureReading> {
    return listOf(
        BloodPressureReading(
            time = "8am",
            systolic = first.first,
            diastolic = first.second
        ),
        BloodPressureReading(
            time = "12pm",
            systolic = second.first,
            diastolic = second.second
        ),
        BloodPressureReading(
            time = "12pm",
            systolic = second.first,
            diastolic = second.second
        )
    )
}

fun Pair<Int, Int>.toHeartRateReadings(): List<HeartRateReading> {
    return listOf(
        HeartRateReading("8am", first),
        HeartRateReading("12pm", second)
    )
}


data class VitalData(
    val temperature: Pair<Double, Double>, // (12:38 PM temp, 6:45 AM temp)
    val heartRate: Pair<Int, Int>, // (12:38 PM, 6:45 AM)
    val bloodPressure: Pair<Pair<Int, Int>, Pair<Int, Int>> // (12:38 PM (systolic, diastolic), 6:45 AM (systolic, diastolic))
)

// Sample Data
val sampleData = VitalData(
    temperature = Pair(102.0, 99.0),
    heartRate = Pair(82, 73),
    bloodPressure = Pair(Pair(117, 75), Pair(128, 77))
)
