package com.chachadev.compose_multiplatform_labs.navigation

import androidx.navigation3.runtime.NavKey

/**
 * Navigator class that handles navigation logic using NavigationState
 */
class Navigator(val state: NavigationState) {

    /**
     * Navigate to a destination. If it's a top-level route, switch to it.
     * Otherwise, navigate within the current top-level route's stack.
     */
    fun navigate(route: NavKey) {
        if (route in state.backStacks.keys) {
            // Top-level route - switch to it
            state.topLevelRoute = route
        } else {
            // Nested route - add to current top-level route's stack
            state.backStacks[state.topLevelRoute]?.add(route)
        }
    }

    /**
     * Navigate back. If we're at the start of current stack and not at start route,
     * switch back to start route. Otherwise, pop from current stack.
     */
    fun goBack(): Boolean {
        val currentStack = state.backStacks[state.topLevelRoute]
            ?: error("Back stack for ${state.topLevelRoute} doesn't exist")
        
        val currentRoute = currentStack.lastOrNull() ?: return false
        
        // If we're at the top-level route itself (stack has only one item)
        // and we're not at the start route, switch back to start route
        if (currentRoute == state.topLevelRoute && state.topLevelRoute != state.startRoute) {
            state.topLevelRoute = state.startRoute
            return true
        }
        
        // Otherwise, try to pop from current stack
        return if (currentStack.size > 1) {
            currentStack.removeLastOrNull()
            true
        } else {
            false
        }
    }

    /**
     * Navigate to a top-level route and clear its stack to start destination
     */
    fun navigateToTopLevel(route: NavKey) {
        if (route in state.backStacks.keys) {
            val stack = state.backStacks[route]
            // Keep only the route itself in the stack
            while (stack?.size ?: 0 > 1) {
                stack?.removeLastOrNull()
            }
            state.topLevelRoute = route
        }
    }

    /**
     * Replace current destination in the stack
     */
    fun replace(destination: NavKey) {
        val currentStack = state.backStacks[state.topLevelRoute]
        currentStack?.removeLastOrNull()
        currentStack?.add(destination)
    }
}