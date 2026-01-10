package com.chachadev.compose_multiplatform_labs.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.chachadev.compose_multiplatform_labs.common.bottom_bar.AppBottomNavigation
import com.chachadev.compose_multiplatform_labs.common.bottom_bar.bottomNavItems
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun RootNavigation(
    modifier: Modifier = Modifier,
    startDestination: NavKey = Destination.Auth
) {
    // Root navigation manages switching between Auth and App
    val rootBackStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Destination.Auth::class, Destination.Auth.serializer())
                    subclass(Destination.App::class, Destination.App.serializer())
                }
            }
        },
        startDestination
    )
    
    // Track current App destination for bottom navigation
    var currentAppDestination by rememberSaveable { 
        mutableStateOf<NavKey>(Destination.App.Home) 
    }
    
    // Show bottom navigation only when on App screen
    val showBottomNav = rootBackStack.last() == Destination.App

    Scaffold(
        bottomBar = {
            if (showBottomNav) {
                AppBottomNavigation(
                    selectedKey = currentAppDestination,
                    onSelectedKey = { destination ->
                        // Navigate within App's nested navigation - this will be handled by AppNavigation
                        currentAppDestination = destination
                    }
                )
            }
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = rootBackStack,
            modifier = modifier.padding(innerPadding),
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<Destination.Auth> {
                    AuthNavigation(
                        onNavigateToApp = {
                            rootBackStack.add(Destination.App)
                        }
                    )
                }
                entry<Destination.App> {
                    AppNavigation(
                        currentDestination = currentAppDestination,
                        onNavigateToDestination = { destination ->
                            currentAppDestination = destination
                        }
                    )
                }
            }
        )
    }
}