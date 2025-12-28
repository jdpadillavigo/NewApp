package com.example.newapp.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val title: String
)

val TOP_LEVEL_DESTINATIONS = mapOf(
    Route.Home to BottomNavItem(
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        title = "Home"
    ),
    Route.Discover to BottomNavItem(
        selectedIcon = Icons.Filled.Language,
        unselectedIcon = Icons.Outlined.Language,
        title = "Discover"
    ),
    Route.Bookmark to BottomNavItem(
        selectedIcon = Icons.Filled.Bookmark,
        unselectedIcon = Icons.Outlined.BookmarkBorder,
        title = "Bookmark"
    ),
    Route.Profile to BottomNavItem(
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        title = "Profile"
    )
)