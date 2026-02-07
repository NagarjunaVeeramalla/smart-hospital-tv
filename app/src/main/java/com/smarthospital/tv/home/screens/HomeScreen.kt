package com.smarthospital.tv.home.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import com.smarthospital.tv.R
import com.smarthospital.tv.home.viewmodels.HomeUiState
import com.smarthospital.tv.home.viewmodels.HomeViewModel
import com.smarthospital.tv.home.data.models.PatientResponseModel
import com.smarthospital.tv.myhealth.ui.theme.SmartHospitalAppTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeComposable(
    viewModel: HomeViewModel = viewModel(),
    onMyHealthClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        when (uiState) {
            is HomeUiState.Loading -> {
                // Show loading indicator or nothing (splash style)
            }

            is HomeUiState.Error -> {
                // Show error message
                Text(text = "Error loading data", color = Color.White)
            }

            is HomeUiState.Success -> {
                val data = (uiState as HomeUiState.Success).patientData
                HomeContent(data, onMyHealthClick)
            }
        }
    }
}

@Composable
fun HomeContent(
    data: PatientResponseModel,
    onMyHealthClick: () -> Unit
) {
    var focusedItem by remember { mutableStateOf("My Health") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        AsyncImage(
            model = R.drawable.home_screen_bg,
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.6f
        )

        // Main Layout Column
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Content Area (Top Bar, Greeting, Info) - Takes available space
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Push BottomMenu to the bottom
                    .padding(top = 48.dp, start = 48.dp, end = 48.dp)
            ) {
                TopBar()

                Spacer(modifier = Modifier.height(32.dp))

                // Greeting
                Text(
                    text = "Good morning, ${data.preferredName}",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Info Area: Dynamic content based on focus
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    when (focusedItem) {
                        "My Health" -> {
                            data.scheduledActivityGroupCounts?.forEach { group ->
                                InfoItem(label = group.groupName, value = group.orderCountRatio)
                            }
                        }
                        "Education" -> {
                            val remaining = data.assignedVideosCount
                            InfoItem(label = "Suggested videos", value = "$remaining Remaining")
                            InfoItem(label = "Availability", value = if (remaining > 0) "All available on your TV" else "Watch again")
                        }
                        "Help & Feedback" -> {
                            // Using a hardcoded help message for now or one from config if available
                            val helpMsg = "Contact Support at 1-800-HOSPITAL" 
                            InfoItem(label = "Help", value = helpMsg)
                        }
                        "Entertainment" -> {
                            InfoItem(label = "Entertainment", value = "Watch movies, TV shows, and more.")
                        }
                        "Display Device" -> {
                            InfoItem(label = "Display Device", value = "Manage your TV settings and connected devices.")
                        }
                        "Comforts" -> {
                            InfoItem(label = "Comforts", value = "Adjust lighting, temperature, and room services.")
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f)) // Push Care Team to bottom

                // Main Info Content
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp), // Space between content and bottom menu
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {

                    // Right Side Info: Care Team
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(32.dp)
                    ) {
                        // Provider
                        val providerName = data.getEdProviderName()
                        if (providerName.isNotEmpty()) {
                            CareTeamItem(role = "Provider", name = providerName)
                        }

                        // PA/NP
                        val paNpName = data.getEdPaNpName("PA/NP")
                        if (paNpName.isNotEmpty()) {
                            CareTeamItem(role = "PA/NP", name = paNpName)
                        }

                        // Nurse
                        val nurseName = data.getEdNurseName()
                        if (nurseName.isNotEmpty()) {
                            CareTeamItem(role = "Nurse", name = nurseName)
                        }

                        // Language
                        if (!data.preferredLanguage.isNullOrEmpty()) {
                            CareTeamItem(role = "Language", name = data.preferredLanguage)
                        }

                        // Room
                        if (!data.location?.displayName.isNullOrEmpty()) {
                            CareTeamItem(role = "Room", name = data.location?.displayName ?: "")
                        }
                    }
                }
            }

            // Bottom Menu (Aligned to bottom by parent Column)
            BottomMenu(
                healthNotificationCount = data.healthNotificationCount,
                assignedVideosCount = data.assignedVideosCount,
                onMyHealthClick = onMyHealthClick,
                onFocusChange = { focusedItem = it }
            )
        }
    }
}


@Composable
fun TopBar() {
    val currentTime = remember {
        SimpleDateFormat("h:mm a", Locale.US).format(Date())
    }
    val currentDate = remember {
        SimpleDateFormat("EEE, MMM d, yyyy", Locale.US).format(Date())
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = currentTime,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = " • ",
            color = Color.White,
            fontSize = 16.sp
        )
        Text(
            text = currentDate,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Text(
        text = "$label: $value",
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal
    )
}

@Composable
fun CareTeamItem(role: String, name: String) {
    Column {
        Text(
            text = role,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = name,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun BottomMenu(
    healthNotificationCount: Int,
    assignedVideosCount: Int,
    onMyHealthClick: () -> Unit,
    onFocusChange: (String) -> Unit
) {
    val menuItems = listOf(
        "My Health",
        "Entertainment",
        "Display Device",
        "Education",
        "Comforts",
        "Help & Feedback",
    )

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF222222).copy(alpha = 0.9f)) // Dark background
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        menuItems.forEachIndexed { index, item ->
            val isMyHealth = item == "My Health"
            val isEducation = item == "Education"

            val badgeCount = when {
                isMyHealth -> healthNotificationCount
                isEducation -> assignedVideosCount
                else -> 0
            }

            key(item) {
                BottomMenuItem(
                    text = item,
                    isSelected = false,
                    hasBadge = badgeCount > 0,
                    badgeCount = badgeCount,
                    onClick = {
                        if (isMyHealth) {
                            onMyHealthClick()
                        }
                        // Handle other clicks if needed
                    },
                    modifier = if (index == 0) Modifier.focusRequester(focusRequester) else Modifier,
                    onFocus = { onFocusChange(item) }
                )
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun BottomMenuItem(
    text: String,
    isSelected: Boolean,
    hasBadge: Boolean = false,
    badgeCount: Int = 0,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onFocus: () -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }

    Button(
        onClick = onClick,
        modifier = modifier
            .onFocusChanged { 
                isFocused = it.isFocused
                if (isFocused) onFocus()
            },
        colors = ButtonDefaults.colors(
            containerColor = Color.Transparent,
            contentColor = Color.White.copy(alpha = 0.8f),
            focusedContainerColor = Color.White,
            focusedContentColor = Color.Black
        ),
        shape = ButtonDefaults.shape(shape = RoundedCornerShape(50)),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp)
        ) {
            Text(
                text = text,
                fontWeight = if (isFocused) FontWeight.Bold else FontWeight.Medium,
                fontSize = 16.sp
            )

            if (hasBadge) {
                Text(
                    text = badgeCount.toString(),
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 4.dp, y = (-7).dp)
                        .background(Color.Red, shape = CircleShape)
                        .padding(horizontal = 2.dp, vertical = 1.dp)
                )
            }
        }
    }
}


@Preview(device = "id:tv_1080p")
@Composable
fun HomeScreenPreview() {
    SmartHospitalAppTheme {
        HomeContent(
            data = com.smarthospital.tv.home.data.HomeStaticData.patientData,
            onMyHealthClick = {}
        )
    }
}

