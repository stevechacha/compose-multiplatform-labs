package com.chachadev.compose_multiplatform_labs.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.chachadev.compose_multiplatform_labs.common.bottom_bar.AppBottomNavigation
import com.chachadev.compose_multiplatform_labs.common.bottom_bar.bottomNavItems

@Composable
fun RootNavigation(
    modifier: Modifier = Modifier,
    startDestination: NavKey = Destination.Auth
) {
    // Define top-level routes: Auth and App
    val topLevelRoutes = setOf(Destination.Auth, Destination.App)
    
    // Create navigation state with top-level routes
    val navigationState = rememberNavigationState(
        startRoute = startDestination,
        topLevelRoutes = topLevelRoutes
    )
    
    // Create navigator using the navigation state
    val navigator = rememberNavigator(navigationState)
    
    // Result store for passing data between screens
    val resultStore = rememberResultStore()
    
    // Get current App destination for bottom navigation
    val currentAppDestination = navigationState.getCurrentNestedDestination(Destination.App) 
        ?: Destination.App.Home
    
    // Show bottom navigation only when authenticated (on App screen)
    val showBottomNav = navigationState.topLevelRoute == Destination.App
    
    Scaffold(
        bottomBar = {
            if (showBottomNav) {
                AppBottomNavigation(
                    selectedKey = currentAppDestination,
                    onSelectedKey = { destination ->
                        // Navigate to selected tab within App's nested navigation
                        navigator.navigate(destination)
                    }
                )
            }
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = modifier.padding(innerPadding),
            onBack = navigator::goBack,
            entries = navigationState.toEntries(
                entryProvider {
                    // Handle Auth nested destinations
                    entry<Destination.Auth.Login> {
                        // Show Login screen directly, but use AuthNavigation for navigation logic
                        AuthNavigation(
                            navigator = navigator,
                            resultStore = resultStore,
                            onNavigateToApp = {
                                navigator.navigateToTopLevel(Destination.App)
                            }
                        )
                    }
                    entry<Destination.Auth.Register> {
                        AuthNavigation(
                            navigator = navigator,
                            resultStore = resultStore,
                            onNavigateToApp = {
                                navigator.navigateToTopLevel(Destination.App)
                            }
                        )
                    }
                    
                    // Handle App nested destinations
                    entry<Destination.App.Home> {
                        AppNavigation(
                            navigator = navigator,
                            resultStore = resultStore
                        )
                    }
                    entry<Destination.App.Profile> {
                        AppNavigation(
                            navigator = navigator,
                            resultStore = resultStore
                        )
                    }
                    entry<Destination.App.Settings> {
                        AppNavigation(
                            navigator = navigator,
                            resultStore = resultStore
                        )
                    }
                    entry<Destination.App.Notifications> {
                        AppNavigation(
                            navigator = navigator,
                            resultStore = resultStore
                        )
                    }
                }
            )
        )
    }
}