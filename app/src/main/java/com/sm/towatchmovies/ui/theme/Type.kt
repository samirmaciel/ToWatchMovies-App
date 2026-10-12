package com.sm.towatchmovies.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val CinemaSans = FontFamily.SansSerif

val Typography = Typography(
    displaySmall = TextStyle(fontFamily = CinemaSans, fontWeight = FontWeight.Bold, fontSize = 36.sp, lineHeight = 42.sp, letterSpacing = (-0.6).sp),
    headlineMedium = TextStyle(fontFamily = CinemaSans, fontWeight = FontWeight.Bold, fontSize = 28.sp, lineHeight = 34.sp, letterSpacing = (-0.3).sp),
    headlineSmall = TextStyle(fontFamily = CinemaSans, fontWeight = FontWeight.SemiBold, fontSize = 24.sp, lineHeight = 30.sp),
    titleLarge = TextStyle(fontFamily = CinemaSans, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, lineHeight = 26.sp),
    titleMedium = TextStyle(fontFamily = CinemaSans, fontWeight = FontWeight.Medium, fontSize = 16.sp, lineHeight = 22.sp),
    titleSmall = TextStyle(fontFamily = CinemaSans, fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp),
    bodyLarge = TextStyle(fontFamily = CinemaSans, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium = TextStyle(fontFamily = CinemaSans, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp),
    bodySmall = TextStyle(fontFamily = CinemaSans, fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 17.sp),
    labelLarge = TextStyle(fontFamily = CinemaSans, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, lineHeight = 20.sp),
    labelMedium = TextStyle(fontFamily = CinemaSans, fontWeight = FontWeight.Medium, fontSize = 12.sp, lineHeight = 16.sp),
    labelSmall = TextStyle(fontFamily = CinemaSans, fontWeight = FontWeight.Medium, fontSize = 11.sp, lineHeight = 14.sp)
)
