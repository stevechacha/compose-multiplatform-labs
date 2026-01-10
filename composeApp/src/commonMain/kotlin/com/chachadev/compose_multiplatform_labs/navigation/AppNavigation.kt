package com.chachadev.compose_multiplatform_labs.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.chachadev.compose_multiplatform_labs.screens.HomeScreen
import com.chachadev.compose_multiplatform_labs.screens.NotificationsScreen
import com.chachadev.compose_multiplatform_labs.screens.ProfileScreen
import com.chachadev.compose_multiplatform_labs.screens.SettingsScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    resultStore: ResultStore
) {
    // Get the App's back stack from navigation state
    val appBackStack = navigator.state.backStacks[Destination.App]
        ?: error("App back stack not found in navigation state")

    NavDisplay(
        backStack = appBackStack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Destination.App.Home> {
                HomeScreen(
                    onNavigateToProfile = {
                        navigator.navigate(Destination.App.Profile)
                    },
                    onNavigateToSettings = {
                        navigator.navigate(Destination.App.Settings)
                    },
                    onNavigateToNotifications = {
                        navigator.navigate(Destination.App.Notifications)
                    }
                )
            }
            entry<Destination.App.Profile> {
                ProfileScreen()
            }
            entry<Destination.App.Settings> {
                SettingsScreen()
            }
            entry<Destination.App.Notifications> {
                NotificationsScreen()
            }
        }
    )
}
