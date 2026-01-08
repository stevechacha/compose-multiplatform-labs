package com.chachadev.compose_multiplatform_labs.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Destination : NavKey {

    @Serializable
    data object Auth : Destination, NavKey {
        @Serializable data object Login : Destination, NavKey
        @Serializable data object Register : Destination, NavKey
    }

    @Serializable data object App : Destination, NavKey {
        @Serializable data object Home : Destination, NavKey
        @Serializable data object Profile : Destination, NavKey
        @Serializable data object Settings : Destination, NavKey
        @Serializable data object Notifications : Destination, NavKey
    }
}
