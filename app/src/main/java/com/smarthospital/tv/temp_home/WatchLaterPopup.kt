package com.smarthospital.tv.temp_home

/*package com.hcahealthcare.stb_android.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button
import androidx.tv.material3.Text
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api

const val TAG_WATCH_LATER = "WatchLaterPopup"
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun WatchLaterPopup(
    onOkayClicked: () -> Unit,
    centerPressedState: Boolean
)
{
    val focusRequester = remember { FocusRequester() }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent, shape = RoundedCornerShape(4.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "When you have a moment, please watch the Welcome video located in the Education section.",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.White,
                fontWeight = FontWeight.Bold

            )
            Button(
                onClick = { onOkayClicked() },
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .focusable(),
                colors = ButtonDefaults.colors(
                    containerColor = Color.White
                ),
                shape = ButtonDefaults.shape(
                    shape = RoundedCornerShape(4.dp)
                )
            ) {
                Box(
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Okay",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
    LaunchedEffect(centerPressedState) {
        if (centerPressedState)
        {
            Log.d(TAG_WATCH_LATER, "Center Button Clicked in Watch Later Popup")
            onOkayClicked()
        }
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}*/