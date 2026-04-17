package com.desafio.instagram_test

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.desafio.designsystem.components.profile.AvatarCircle
import com.desafio.designsystem.components.profile.ProfileStatItem
import com.desafio.designsystem.components.profile.StoryHighlight
import com.desafio.designsystem.theme.BillingTheme
import com.desafio.designsystem.theme.BillingThemeTokens

// ── Screen ────────────────────────────────────────────────────────────────────

@Composable
fun InstagramProfileScreen() {
    val igColors = BillingThemeTokens.instagramColors

    Scaffold(
        containerColor = igColors.background,
        bottomBar = { ProfileBottomTabBar() },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
        ) {
            ProfileTopBar()
            HorizontalDivider(color = igColors.divider, thickness = 0.5.dp)
            ProfileInfoSection()
            HorizontalDivider(color = igColors.divider, thickness = 0.5.dp)
            ProfileStoriesRow()
            HorizontalDivider(color = igColors.divider, thickness = 0.5.dp)
            ProfilePostsTabBar()
            HorizontalDivider(color = igColors.divider, thickness = 0.5.dp)
            ProfilePostsGrid()
        }
    }
}

// ── Top Bar ───────────────────────────────────────────────────────────────────

@Composable
private fun ProfileTopBar() {
    val igColors  = BillingThemeTokens.instagramColors
    val spacing   = BillingThemeTokens.spacing
    val typography = BillingThemeTokens.typography

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .padding(horizontal = spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Username + lock + account switcher
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = "jacob_w",
                style = typography.titleMedium,
                color = igColors.textPrimary,
            )
            Spacer(Modifier.width(spacing.xs))
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Conta privada",
                tint = igColors.textPrimary,
                modifier = Modifier.size(12.dp),
            )
            Spacer(Modifier.width(spacing.xs))
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Trocar conta",
                tint = igColors.textPrimary,
                modifier = Modifier.size(16.dp),
            )
        }
        // Hamburger menu
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Menu",
            tint = igColors.textPrimary,
            modifier = Modifier.size(22.dp),
        )
    }
}

// ── Profile Info ──────────────────────────────────────────────────────────────

@Composable
private fun ProfileInfoSection() {
    val igColors   = BillingThemeTokens.instagramColors
    val spacing    = BillingThemeTokens.spacing
    val shapes     = BillingThemeTokens.shapes
    val typography = BillingThemeTokens.typography

    Column(modifier = Modifier.fillMaxWidth()) {

        // Avatar + Stats row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.lg, vertical = spacing.md),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Avatar — 96dp outer (matches Figma: 86dp inner + 5dp padding + 1.5dp ring)
            AvatarCircle(size = 96.dp) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(igColors.placeholder),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Foto de perfil",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp),
                    )
                }
            }

            Spacer(Modifier.width(spacing.xl2))

            // Stats
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                ProfileStatItem(count = "54",  label = "Posts")
                ProfileStatItem(count = "834", label = "Followers")
                ProfileStatItem(count = "162", label = "Following")
            }
        }

        // Name & Bio
        Column(modifier = Modifier.padding(horizontal = spacing.lg)) {
            Text(
                text = "Jacob West",
                style = typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = igColors.textPrimary,
            )
            Spacer(Modifier.height(spacing.xs))
            Text(
                text = "Digital goodies designer @pixsellz\nEverything is designed.",
                style = typography.bodySmall,
                color = igColors.textPrimary,
            )
        }

        Spacer(Modifier.height(spacing.md))

        // Edit Profile button — full width, 6dp radius, translucent border
        OutlinedButton(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.lg)
                .height(29.dp),
            shape = shapes.profileButton,
            border = androidx.compose.foundation.BorderStroke(1.dp, igColors.borderVariant),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = igColors.background,
                contentColor = igColors.textPrimary,
            ),
            contentPadding = PaddingValues(horizontal = spacing.lg),
        ) {
            Text(
                text = "Edit Profile",
                style = typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                color = igColors.textPrimary,
            )
        }

        Spacer(Modifier.height(spacing.md))
    }
}

// ── Stories Row ───────────────────────────────────────────────────────────────

private data class StoryData(val name: String, val isNew: Boolean = false)

@Composable
private fun ProfileStoriesRow() {
    val spacing = BillingThemeTokens.spacing

    val stories = listOf(
        StoryData("New",     isNew = true),
        StoryData("Friends"),
        StoryData("Sport"),
        StoryData("Design"),
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.md, vertical = spacing.sm),
        horizontalArrangement = Arrangement.spacedBy(spacing.lg),
    ) {
        stories.forEach { story ->
            StoryHighlight(
                name  = story.name,
                isNew = story.isNew,
                size  = 64.dp,
            )
        }
    }
}

// ── Posts Tab Bar ─────────────────────────────────────────────────────────────

@Composable
private fun ProfilePostsTabBar() {
    val igColors = BillingThemeTokens.instagramColors

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        // Grid tab — active
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.GridOn,
                    contentDescription = "Posts",
                    tint = igColors.tabActive,
                    modifier = Modifier.size(22.dp),
                )
                Spacer(Modifier.height(2.dp))
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(1.dp)
                        .background(igColors.tabActive),
                )
            }
        }

        // Tagged tab — inactive
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.PersonPin,
                contentDescription = "Marcações",
                tint = igColors.tabInactive.copy(alpha = 0.4f),
                modifier = Modifier.size(22.dp),
            )
        }
    }
}

// ── Posts Grid ────────────────────────────────────────────────────────────────

@Composable
private fun ProfilePostsGrid() {
    val igColors = BillingThemeTokens.instagramColors
    val videoPostIndex = 4 // centro da grade — Figma: Video Post

    Column {
        for (row in 0 until 3) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (col in 0 until 3) {
                    val index = row * 3 + col
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(0.5.dp)
                            .background(igColors.placeholder),
                        contentAlignment = Alignment.TopEnd,
                    ) {
                        if (index == videoPostIndex) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Vídeo",
                                tint = Color.White,
                                modifier = Modifier
                                    .padding(6.dp)
                                    .size(18.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

// ── Bottom Tab Bar ────────────────────────────────────────────────────────────

@Composable
private fun ProfileBottomTabBar() {
    val igColors = BillingThemeTokens.instagramColors
    val spacing  = BillingThemeTokens.spacing

    Column {
        HorizontalDivider(color = igColors.divider, thickness = 0.5.dp)
        NavigationBar(
            containerColor = igColors.surface,
            tonalElevation = 0.dp,
            modifier = Modifier.height(50.dp),
        ) {
            ProfileTabItem(Icons.Default.Home,         "Home",      selected = false, igColors.tabActive, igColors.tabInactive)
            ProfileTabItem(Icons.Default.Search,       "Busca",     selected = false, igColors.tabActive, igColors.tabInactive)
            ProfileTabItem(Icons.Default.AddBox,       "Reels",     selected = false, igColors.tabActive, igColors.tabInactive)
            ProfileTabItem(Icons.Default.FavoriteBorder,"Atividade",selected = false, igColors.tabActive, igColors.tabInactive)
            ProfileTabItem(Icons.Default.Person,       "Perfil",    selected = true,  igColors.tabActive, igColors.tabInactive)
        }
    }
}

@Composable
private fun RowScope.ProfileTabItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    activeColor: Color,
    inactiveColor: Color,
) {
    NavigationBarItem(
        selected = selected,
        onClick  = {},
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(24.dp),
            )
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor   = activeColor,
            unselectedIconColor = inactiveColor,
            indicatorColor      = Color.Transparent,
        ),
    )
}

// ── Preview ───────────────────────────────────────────────────────────────────

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InstagramProfileScreenPreview() {
    BillingTheme {
        InstagramProfileScreen()
    }
}
