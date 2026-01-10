package com.chachadev.compose_multiplatform_labs.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

/**
 * Extension function to navigate within a NavBackStack with optional result
 */
fun NavBackStack<NavKey>.navigateTo(destination: NavKey) {
    add(destination)
}

/**
 * Extension function to check if a destination is currently active
 */
fun NavigationState.isCurrentDestination(destination: NavKey): Boolean {
    return topLevelRoute == destination || 
           backStacks[topLevelRoute]?.lastOrNull() == destination
}

/**
 * Extension function to get the current nested destination within a top-level route
 */
fun NavigationState.getCurrentNestedDestination(route: NavKey): NavKey? {
    return backStacks[route]?.lastOrNull()
}

/**
 * Extension function to check if back navigation is possible
 */
fun NavigationState.canGoBack(): Boolean {
    val currentStack = backStacks[topLevelRoute] ?: return false
    return currentStack.size > 1 || (topLevelRoute != startRoute)
}

/**
 * Composable function to remember Navigator with NavigationState
 */
@Composable
fun rememberNavigator(state: NavigationState): Navigator {
    return remember(state) {
        Navigator(state)
    }
}

/**
 * Extension to navigate and set result
 */
fun Navigator.navigateWithResult(
    destination: NavKey,
    resultStore: ResultStore? = null,
    resultKey: Any? = null,
    result: Any? = null
) {
    if (resultStore != null && resultKey != null && result != null) {
        resultStore.setResult(resultKey, result)
    }
    navigate(destination)
}

