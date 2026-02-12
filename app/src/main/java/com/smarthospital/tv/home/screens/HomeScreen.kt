package com.smarthospital.tv.home.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.smarthospital.tv.R
import com.smarthospital.tv.home.data.HomeStaticData
import com.smarthospital.tv.home.data.models.PatientResponseModel
import com.smarthospital.tv.home.viewmodels.HomeMenuItem
import com.smarthospital.tv.home.viewmodels.HomeUiState
import com.smarthospital.tv.home.viewmodels.HomeViewModel
import com.smarthospital.tv.home.viewmodels.AppBottomMenu
import com.smarthospital.tv.myhealth.ui.theme.SmartHospitalAppTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeComposable(
    viewModel: HomeViewModel = viewModel(),
    onMenuItemClick: (HomeMenuItem) -> Unit
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
                val state = uiState as HomeUiState.Success
                val showIntroPopup by viewModel.showIntroPopup.collectAsState()
                val showWatchLaterPopup by viewModel.showWatchLaterPopup.collectAsState()
                val isPopupVisible = showIntroPopup || showWatchLaterPopup

                HomeContent(
                    data = state.patientData,
                    menuItems = state.menuItems,
                    onMenuItemClick = onMenuItemClick,
                    isContentEnabled = !isPopupVisible
                )

                val introVideo by viewModel.introVideo.collectAsState()

                if (showIntroPopup && introVideo != null) {
                    AppIntroVideoPopUp(
                        onPlayClick = viewModel::onIntroPlayClicked,
                        imageRes = introVideo!!.thumbnailurl,
                        title = introVideo!!.title,
                        videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
                        onWatchLaterClicked = viewModel::onIntroWatchLaterClicked,
                        onVideoFinished = viewModel::onIntroVideoFinished
                    )
                }

                if (showWatchLaterPopup) {
                    WatchLaterPopup(
                        onOkayClicked = viewModel::onWatchLaterOkayClicked
                    )
                }
            }
        }
    }
}

@Composable
fun HomeContent(
    data: PatientResponseModel,
    menuItems: List<HomeMenuItem>,
    onMenuItemClick: (HomeMenuItem) -> Unit,
    isContentEnabled: Boolean = true
) {
    var focusedItem by remember { mutableStateOf(AppBottomMenu.MyCare) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        val context = LocalContext.current
        
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data.config.backgroundImageUrl)
                .listener(
                    onStart = { println("Coil: Starting image load for ${data.config.backgroundImageUrl}") },
                    onSuccess = { _, _ -> println("Coil: Image loaded successfully") },
                    onError = { _, result -> println("Coil: Image load failed: ${result.throwable.message}") ; result.throwable.printStackTrace() }
                )
                .placeholder(R.drawable.home_screen_bg)
                .error(R.drawable.home_screen_bg)
                .crossfade(true)
                .build(),
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
                        AppBottomMenu.MyCare -> {
                            data.scheduledActivityGroupCounts?.forEach { group ->
                                InfoItem(label = group.groupName, value = group.orderCountRatio)
                            }
                        }

                        AppBottomMenu.Learning -> {
                            val remaining = data.assignedVideosCount
                            InfoItem(label = "Suggested videos", value = "$remaining Remaining")
                            InfoItem(
                                label = "Availability",
                                value = if (remaining > 0) "All available on your TV" else "Watch again"
                            )
                        }

                        AppBottomMenu.Support -> {
                            // Using a hardcoded help message for now or one from config if available
                            val helpMsg = "Contact Support at 1-800-HOSPITAL"
                            InfoItem(label = "Help", value = helpMsg)
                        }

                        AppBottomMenu.Entertainment -> {
                            InfoItem(
                                label = "Entertainment",
                                value = "Watch movies, TV shows, and more."
                            )
                        }

                        AppBottomMenu.TvSettings -> {
                            InfoItem(
                                label = "TV Settings",
                                value = "Manage your TV settings and connected devices."
                            )
                        }

                        AppBottomMenu.RoomControl -> {
                            InfoItem(
                                label = "Room Control",
                                value = "Adjust lighting, temperature, and room services."
                            )
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
                            CareTeamItem(role = "Room", name = data.location.displayName)
                        }
                    }
                }
            }

            // Bottom Menu (Aligned to bottom by parent Column)
            // Bottom Menu (Aligned to bottom by parent Column)
            BottomMenu(
                menuItems = menuItems,
                onMenuItemClick = onMenuItemClick,
                onFocusChange = { focusedItem = it },
                isEnabled = isContentEnabled
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
    menuItems: List<HomeMenuItem>,
    onMenuItemClick: (HomeMenuItem) -> Unit,
    onFocusChange: (AppBottomMenu) -> Unit,
    isEnabled: Boolean = true
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isEnabled) {
        if (isEnabled) {
            focusRequester.requestFocus()
        }
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
            key(item.id) {
                BottomMenuItem(
                    text = item.displayName,
                    hasBadge = item.badgeCount > 0,
                    badgeCount = item.badgeCount,
                    onClick = {
                        onMenuItemClick(item)
                    },
                    modifier = if (index == 0) Modifier.focusRequester(focusRequester) else Modifier,
                    onFocus = { onFocusChange(item.type) },
                    isEnabled = isEnabled
                )
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun BottomMenuItem(
    modifier: Modifier = Modifier,
    text: String,
    hasBadge: Boolean = false,
    badgeCount: Int = 0,
    onClick: () -> Unit,
    onFocus: () -> Unit,
    isEnabled: Boolean = true
) {
    var isFocused by remember { mutableStateOf(false) }

    LaunchedEffect(isEnabled) {
        if (!isEnabled) {
            isFocused = false
        }
    }

    Box(
        modifier = modifier
            .bottomMenuItemFocus(
                isFocused = isFocused,
                onFocusChange = {
                    isFocused = it
                    if (it) onFocus()
                },
                onClick = onClick,
                enabled = isEnabled
            )
            .padding(horizontal = 4.dp, vertical = 3.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
             modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = text,
                fontWeight = if (isFocused) FontWeight.Bold else FontWeight.Medium,
                fontSize = 16.sp,
                color = if (isFocused) Color.Black else Color.White.copy(alpha = 0.8f)
            )

            if (hasBadge) {
                Text(
                    text = badgeCount.toString(),
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 12.dp, y = (-8).dp)
                        .background(Color.Red, shape = CircleShape)
                        .padding(horizontal = 4.dp, vertical = 1.dp)
                )
            }
        }
    }
}

private fun Modifier.bottomMenuItemFocus(
    isFocused: Boolean,
    onFocusChange: (Boolean) -> Unit,
    onClick: () -> Unit,
    enabled: Boolean
): Modifier = composed {
    val modifier = if (enabled) {
        this
            .onFocusChanged { onFocusChange(it.isFocused || it.hasFocus) }
            .focusable()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    } else {
        this
    }

    modifier.background(
        color = if (isFocused && enabled) Color.White else Color.Transparent,
        shape = RoundedCornerShape(50)
    )
}



@Preview(device = "id:tv_1080p")
@Composable
fun HomeScreenPreview() {
    val mockMenuItems = listOf(
        HomeMenuItem(type = AppBottomMenu.MyCare, badgeCount = 2),
        HomeMenuItem(type = AppBottomMenu.Entertainment),
        HomeMenuItem(type = AppBottomMenu.TvSettings),
        HomeMenuItem(type = AppBottomMenu.Learning, badgeCount = 10),
        HomeMenuItem(type = AppBottomMenu.RoomControl),
        HomeMenuItem(type = AppBottomMenu.Support)
    )

    SmartHospitalAppTheme {
        HomeContent(
            data = HomeStaticData.patientData,
            menuItems = mockMenuItems,
            onMenuItemClick = {}
        )
    }
}

