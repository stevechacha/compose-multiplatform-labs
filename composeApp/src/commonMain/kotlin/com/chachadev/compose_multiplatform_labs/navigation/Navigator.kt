package com.chachadev.compose_multiplatform_labs.navigation

import androidx.navigation3.runtime.NavKey
import com.chachadev.compose_multiplatform_labs.common.bottom_bar.NavigationState

class Navigator(val state: NavigationState) {

    fun navigate(route: NavKey) {
        if(route in state.backStacks.keys) {
            state.topLevelRoute = route
        } else {
            state.backStacks[state.topLevelRoute]?.add(route)
        }
    }

    fun goBack() {
        val currentStack = state.backStacks[state.topLevelRoute]
            ?: error("Back stack for ${state.topLevelRoute} doesn't exist")
        val currentRoute = currentStack.last()

        if(currentRoute == state.topLevelRoute) {
            state.topLevelRoute = state.startRoute
        } else {
            currentStack.removeLastOrNull()
        }
    }
}