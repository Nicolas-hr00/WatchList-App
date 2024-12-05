package com.plcoding.stockmarketapp.ui.theme

import android.graphics.Color.parseColor
import androidx.compose.ui.graphics.Color

val DarkBlue = Color(0xFF060D2E)
val TextWhite = Color(0xFFEEEEEE)
val DarkYellow = "#f0f0bd".color
val DarkBrown = "#988558".color

// Extension property to convert hex string to Color
val String.color: Color
    get() = Color(android.graphics.Color.parseColor(this))
