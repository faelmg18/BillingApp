package com.desafio.designsystem.tokens

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Semantic color tokens specific to the Instagram-style profile UI.
 * These complement [BillingColors] for features that follow a neutral/monochromatic palette.
 */
@Immutable
data class InstagramColors(
    /** Page background — pure white */
    val background: Color,
    /** Tab bar / secondary surface — off-white */
    val surface: Color,
    /** Primary text and icons */
    val textPrimary: Color,
    /** Secondary / caption text and inactive icons */
    val textSecondary: Color,
    /** Story ring / avatar ring border */
    val border: Color,
    /** Edit Profile button stroke — translucent dark */
    val borderVariant: Color,
    /** Horizontal rule between sections */
    val divider: Color,
    /** Image / avatar placeholder fill */
    val placeholder: Color,
    /** Active bottom-tab indicator text/icon */
    val tabActive: Color,
    /** Inactive bottom-tab icon */
    val tabInactive: Color,
)

val LightInstagramColors = InstagramColors(
    background    = ColorTokens.White,
    surface       = ColorTokens.Neutral50,
    textPrimary   = ColorTokens.Neutral600,
    textSecondary = ColorTokens.Neutral400,
    border        = ColorTokens.Neutral300,
    borderVariant = Color(0x2E3C3C43),   // rgba(60,60,67,0.18) — Figma stroke
    divider       = ColorTokens.Neutral300,
    placeholder   = ColorTokens.Neutral200,
    tabActive     = ColorTokens.Neutral600,
    tabInactive   = ColorTokens.Neutral600,
)

val LocalInstagramColors = staticCompositionLocalOf { LightInstagramColors }
