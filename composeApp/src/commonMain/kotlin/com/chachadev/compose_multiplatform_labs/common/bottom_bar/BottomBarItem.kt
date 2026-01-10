package com.chachadev.compose_multiplatform_labs.common.bottom_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.chachadev.compose_multiplatform_labs.navigation.Destination

data class BottomBarItem(
    val title: String ,
    val drawableIcon: ImageVector
)

val bottomNavItems = mapOf(
    Destination.App.Home to  BottomBarItem(
        title = "Home",
        drawableIcon = Icons.Default.Home
    ),
    Destination.App.Profile to BottomBarItem(
        title = "Search",
        drawableIcon = Icons.Default.Search
    ),
    Destination.App.Settings to  BottomBarItem(
        title = "Profile",
        drawableIcon = Icons.Default.Person
    )
)
