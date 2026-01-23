package com.smarthospital.tv.feedback.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.smarthospital.tv.R

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ShareFeedbackDialog(
    modifier: Modifier = Modifier,
    onShareFeedbackClick: () -> Unit,
    onNotNowClick: () -> Unit
) {
    val shareFeedbackFocusRequester = remember { FocusRequester() }
    val notNowFocusRequester = remember { FocusRequester() }
    var isShareFeedbackFocused by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        shareFeedbackFocusRequester.requestFocus()
    }

    Box(
        modifier = modifier
            .width(988.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF2C2C2C))
            .padding(vertical = 40.dp, horizontal = 48.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.share_feedback_title),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                )
                Text(
                    text = stringResource(R.string.share_feedback_description),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White.copy(alpha = 0.9f),
                        lineHeight = 24.sp,
                        fontSize = 18.sp
                    )
                )
            }

            Column(
                modifier = Modifier.width(280.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TvButton(
                    text = stringResource(R.string.share_feedback_button),
                    isFocused = isShareFeedbackFocused,
                    focusRequester = shareFeedbackFocusRequester,
                    onFocusChanged = { if (it) isShareFeedbackFocused = true },
                    onClick = onShareFeedbackClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )

                TvButton(
                    text = stringResource(R.string.not_now_button),
                    isFocused = !isShareFeedbackFocused,
                    focusRequester = notNowFocusRequester,
                    onFocusChanged = { if (it) isShareFeedbackFocused = false },
                    onClick = onNotNowClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
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
    focusRequester: FocusRequester,
    onFocusChanged: (Boolean) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { onFocusChanged(it.isFocused) },
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
                )
            )
        }
    }
}

@Preview(device = "id:tv_1080p")
@Composable
fun ShareFeedbackDialogPreview() {
    ShareFeedbackDialog(onShareFeedbackClick = {}, onNotNowClick = {})
}
