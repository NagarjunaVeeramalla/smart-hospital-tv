package com.smarthospital.tv.datamodels


data class HospitalDataModel(

    val vitalSigns: List<Vital>? = emptyList(),
    val careTeam: List<CareTeam>? = emptyList(),
    val scheduledActivities: List<ScheduledActivity>? = emptyList(),
    val scheduledActivityGroupCounts: List<ScheduledActivityGroup>? = emptyList(),
    val staffHistory: List<StaffHistory>? = emptyList(),
    val careTeamED: List<CareTeam>? = emptyList(),
    val generalInfo: List<GeneralInfo>? = emptyList(),
    val isFeedbackComplete: Boolean = false

) {

    data class Vital(
        val displayName: String,
        val hasData: Boolean,
        val compoundSeparator: String?,
        val unit: String?,
        val cardTitle: String?,
        val cardDescription: String?,
        val measurements: List<Measurement>?
    )

    data class Measurement(
        val captureDateTime: String,
        val values: List<Double>
    )

    data class CareTeam(
        val hca34: String?,
        val firstName: String,
        val lastName: String,
        val slot: String?,
        val clinicalRole: String?,
        val assignmentType: String?
    )

    data class ScheduledActivity(
        val type: String,
        val id: String,
        val title: String,
        val description: String,
        val scheduledDateTime: String
    )

    data class ScheduledActivityGroup(
        val groupName: String,
        val orderCountRatio: String
    )

    data class StaffHistory(
        val enteredDateTime: String,
        val firstName: String,
        val staffType: String
    )

    data class GeneralInfo(
        val title: String,
        val values: List<String>?,
        val card: Card?
    )

    data class Card(
        val title: String,
        val description: String,
        val imageUrl: String
    )
}

fun HospitalDataModel.Vital.toHeartRateReadings(): List<HeartRateReading> {
    return measurements
        ?.mapNotNull { measurement ->
            val bpm = measurement.values.firstOrNull()?.toInt() ?: return@mapNotNull null
            HeartRateReading(
                time = measurement.captureDateTime.toDisplayTime(),
                bpm = bpm
            )
        }
        ?: emptyList()
}

fun HospitalDataModel.Vital.toTemperatureReadings(): List<TemperatureReading> {
    return measurements
        ?.mapNotNull { measurement ->
            val tempF = measurement.values.firstOrNull() ?: return@mapNotNull null
            TemperatureReading(
                time = measurement.captureDateTime.toDisplayTime(),
                tempF = tempF
            )
        }
        ?: emptyList()
}

fun HospitalDataModel.Vital.toBloodPressureReadings(): List<BloodPressureReading> {
    return measurements
        ?.mapNotNull { measurement ->
            if (measurement.values.size < 2) return@mapNotNull null

            BloodPressureReading(
                time = measurement.captureDateTime.toDisplayTime(),
                systolic = measurement.values[0].toInt(),
                diastolic = measurement.values[1].toInt()
            )
        }
        ?: emptyList()
}

fun String.toDisplayTime(): String {
    return try {
        java.time.OffsetDateTime.parse(this)
            .toLocalTime()
            .withSecond(0)
            .toString()
    } catch (e: Exception) {
        "--"
    }
}
