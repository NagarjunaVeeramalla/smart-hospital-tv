package com.smarthospital.tv.temp_home

/*
@file:OptIn(ExperimentalTvMaterial3Api::class)

package com.hcahealthcare.stb_android.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text

@Composable
fun AppIntroVideoPopUp(
    onPlayClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageRes: String,
    title: String,
    onWatchLaterClicked: () -> Unit,
    leftPressedState: Boolean,
    rightPressedState: Boolean,
    centerPressedState: Boolean,
) {
    val playNowFocusRequester = remember { FocusRequester() }
    val watchLaterFocusRequester = remember { FocusRequester() }
    var isPlayNowFocused by remember { mutableStateOf(true) }
    var welcomePart = ""
    var locationPart = ""
    title?.takeIf { it.isNotEmpty() }?.let {
        val parts = it.split(" to ")
        welcomePart = if (parts.size > 1) "${parts[0]} to" else it
        locationPart = if (parts.size > 1) parts[1] else ""
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = modifier
                    .weight(0.8f)
                    .fillMaxSize()
            ) {
                ThumbnailImage(
                    imageRes,
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 25.sp)) {
                            append("$welcomePart\n")
                        }
                        withStyle(style = SpanStyle(fontSize = 35.sp)) {
                            append(locationPart)
                        }
                    },
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
                    .padding(start = 42.dp, end = 42.dp)
            ) {
                TvButton(
                    text = "Play Now",
                    isFocused = isPlayNowFocused,
                    focusRequester = playNowFocusRequester,
                    onClick = { },
                    modifier = Modifier.weight(0.5f).padding(16.dp)
                )
                TvButton(
                    text = "Watch Later",
                    isFocused = !isPlayNowFocused,
                    focusRequester = watchLaterFocusRequester,
                    onClick = { },
                    modifier = Modifier.weight(0.5f).padding(16.dp)
                )
            }
        }
    }

    LaunchedEffect(leftPressedState) {
        if (leftPressedState) {
            if (!isPlayNowFocused) {
                isPlayNowFocused = true
                playNowFocusRequester.requestFocus()
            }
        }
    }

    LaunchedEffect(rightPressedState) {
        if (rightPressedState) {
            if (isPlayNowFocused) {
                isPlayNowFocused = false
                watchLaterFocusRequester.requestFocus()
            }
        }
    }
    LaunchedEffect(centerPressedState) {
        if (centerPressedState) {
            if (isPlayNowFocused) {
                onPlayClick()
            } else {
                onWatchLaterClicked()
            }
        }
    }
}

@Composable
fun TvButton(
    text: String,
    isFocused: Boolean,
    focusRequester: FocusRequester,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { onClick() },
        modifier = modifier
            .focusRequester(focusRequester)
            .focusable(),
        colors = ButtonDefaults.colors(
            containerColor = if (isFocused) Color.White else Color.White.copy(alpha = 0.5F)
        ),
        shape = ButtonDefaults.shape(
            shape = RoundedCornerShape(4.dp)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}*/
