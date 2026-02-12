package com.smarthospital.tv.home.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.smarthospital.tv.R
import com.smarthospital.tv.components.VideoPlayer

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun AppIntroVideoPopUp(
    onPlayClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageRes: String,
    title: String,
    videoUrl: String,
    onWatchLaterClicked: () -> Unit,
    onVideoFinished: () -> Unit
) {
    val playNowFocusRequester = remember { FocusRequester() }
    val watchLaterFocusRequester = remember { FocusRequester() }
    var isPlayNowFocused by remember { mutableStateOf(true) }
    var isPlaying by remember { mutableStateOf(false) }
    var welcomePart = ""
    var locationPart = ""
    title.takeIf { it.isNotEmpty() }?.let {
        val parts = it.split(" to ")
        welcomePart = if (parts.size > 1) "${parts[0]} to" else it
        locationPart = if (parts.size > 1) parts[1] else ""
    }

    LaunchedEffect(Unit) {
        if (!isPlaying) {
            playNowFocusRequester.requestFocus()
        }
    }

    Dialog(
        onDismissRequest = { /* Prevent dismissal by clicking outside */ },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f)), // Overlay background for the whole screen
            contentAlignment = Alignment.Center
        ) {
            // The Popup Dialog Container
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.75f) // Reduced width slightly
                    .background(Color(0xFF2C2C2C), RoundedCornerShape(6.dp)) // Dialog background
                    .clip(RoundedCornerShape(6.dp))
            ) {
                if (isPlaying) {
                   VideoPlayer(
                        videoUrl = videoUrl,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f),
                        showControls = false,
                        onVideoEnded = {
                            isPlaying = false // Reset local state
                            onVideoFinished() // Notify parent to dismiss
                        }
                    )
                } else {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f) // Image maintains aspect ratio
                        ) {
                            // Background Image
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(imageRes)
                                    .listener(
                                        onStart = { println("Coil: Starting image load for $imageRes") },
                                        onSuccess = { _, _ -> println("Coil: Image loaded successfully") },
                                        onError = { _, result ->
                                            println("Coil: Image load failed: ${result.throwable.message}")
                                            result.throwable.printStackTrace()
                                        }
                                    )
                                    .placeholder(R.drawable.home_screen_bg)
                                    .error(R.drawable.home_screen_bg)
                                    .build(),
                                contentDescription = "Background",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                alpha = 1f // Opaque image
                            )

                            // Scrim / Background Shade
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.5f)) // Add shade for text readability
                            )

                            // Title Text Overlay
                            Column(
                                modifier = Modifier
                                    .padding(24.dp)
                                    .align(Alignment.TopStart)
                            ) {
                                Text(
                                    text = welcomePart,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = locationPart,
                                    fontSize = 35.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        // Bottom Section: Buttons
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp), // Padding for buttons area
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TvButton(
                                text = "Play Now",
                                isFocused = isPlayNowFocused,
                                onClick = { isPlaying = true },
                                modifier = Modifier
                                    .weight(1f)
                                    .focusRequester(playNowFocusRequester)
                                    .onFocusChanged { if (it.isFocused) isPlayNowFocused = true }
                            )
                            TvButton(
                                text = "Watch Later",
                                isFocused = !isPlayNowFocused,
                                onClick = onWatchLaterClicked,
                                modifier = Modifier
                                    .weight(1f)
                                    .focusRequester(watchLaterFocusRequester)
                                    .onFocusChanged { if (it.isFocused) isPlayNowFocused = false }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun WatchLaterPopup(
    onOkayClicked: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(
        modifier = Modifier
            .fillMaxSize() // Full screen to block interactions behind
            .background(Color.Black.copy(alpha = 0.8f))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
             modifier = Modifier
                .background(Color(0xFF333333), shape = RoundedCornerShape(12.dp)) // Popup background
                .padding(32.dp)
        ) {
            Text(
                text = "When you have a moment, please watch the Welcome video located in the Education section.",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 24.dp),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Button(
                onClick = onOkayClicked,
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .focusable(),
                colors = ButtonDefaults.colors(
                    containerColor = Color.White,
                    focusedContainerColor = Color.White,
                     focusedContentColor = Color.Black
                ),
                shape = ButtonDefaults.shape(
                    shape = RoundedCornerShape(4.dp)
                )
            ) {
                Text(
                    text = "Okay",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
            }
        }
    }
}




@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun TvButton(
    text: String,
    isFocused: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.colors(
            containerColor = if (isFocused) Color.White else Color(0xFF4F4F4F),
            contentColor = if (isFocused) Color.Black else Color.White,
            focusedContainerColor = Color.White,
            focusedContentColor = Color.Black
        ),
        shape = ButtonDefaults.shape(RoundedCornerShape(4.dp)),
        scale = ButtonDefaults.scale(focusedScale = 1f)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Preview(device = "id:tv_1080p")
@Composable
fun AppIntroVideoPopUpPreview() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        AppIntroVideoPopUp(
            onPlayClick = {},
            imageRes = "", // Mock image res
            title = "Welcome to Smart Hospital to Your Room",
            videoUrl = "",
            onWatchLaterClicked = {},
            onVideoFinished = {}
        )
    }
}

@Preview(device = "id:tv_1080p")
@Composable
fun WatchLaterPopupPreview() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        WatchLaterPopup(
            onOkayClicked = {}
        )
    }
}
