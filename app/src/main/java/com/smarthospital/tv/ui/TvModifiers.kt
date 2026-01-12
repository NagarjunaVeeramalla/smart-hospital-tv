package com.smarthospital.tv.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A common modifier for TV cards that applies a scale animation
 * and a radial gradient highlight when focused.
 */
fun Modifier.tvFocusDesign(
    width: Dp,
    shape: Shape = RoundedCornerShape(16.dp),
    baseColor: Color = Color(0xFF1E1E1E)
): Modifier = composed {
    var isFocused by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    
    val radiusPx = with(density) { 350.dp.toPx() }
    val widthPx = with(density) { width.toPx() }

    // Refined shine colors derived from the XML spec
    // We use a bit more opacity when focused as requested
    val shineStart = if (isFocused) Color(0x28FFFFFF) else Color(0x10FFFFFF)
    val shineEnd = if (isFocused) Color(0x1A000000) else Color(0x0A000000)

    this
        .onFocusChanged {
            isFocused = it.isFocused || it.hasFocus
        }
        .focusable()
        .scale(if (isFocused) 1.08f else 1f)
        .background(color = baseColor, shape = shape) // Base surface
        .background( // Radial shine overlay
            brush = Brush.radialGradient(
                colors = listOf(shineStart, shineEnd),
                center = Offset(widthPx / 2, 0f),
                radius = radiusPx
            ),
            shape = shape
        )
}
