package com.chachadev.compose_multiplatform_labs.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.chachadev.compose_multiplatform_labs.screens.HomeScreen
import com.chachadev.compose_multiplatform_labs.screens.NotificationsScreen
import com.chachadev.compose_multiplatform_labs.screens.ProfileScreen
import com.chachadev.compose_multiplatform_labs.screens.SettingsScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {
    val appBackStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Destination.App.Home::class, Destination.App.Home.serializer())
                    subclass(Destination.App.Profile::class, Destination.App.Profile.serializer())
                    subclass(Destination.App.Settings::class, Destination.App.Settings.serializer())
                    subclass(Destination.App.Notifications::class, Destination.App.Notifications.serializer())
                }
            }
        },
        Destination.App.Home
    )

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
                        appBackStack.add(Destination.App.Profile)
                    },
                    onNavigateToSettings = {
                        appBackStack.add(Destination.App.Settings)
                    },
                    onNavigateToNotifications = {
                        appBackStack.add(Destination.App.Notifications)
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
