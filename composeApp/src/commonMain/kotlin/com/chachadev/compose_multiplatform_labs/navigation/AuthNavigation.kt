package com.chachadev.compose_multiplatform_labs.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.chachadev.compose_multiplatform_labs.screens.LoginScreen
import com.chachadev.compose_multiplatform_labs.screens.RegisterScreen

@Composable
fun AuthNavigation(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    resultStore: ResultStore,
    onNavigateToApp: () -> Unit = {}
) {
    // Get the Auth's back stack from navigation state
    val authBackStack = navigator.state.backStacks[Destination.Auth]
        ?: error("Auth back stack not found in navigation state")

    NavDisplay(
        backStack = authBackStack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Destination.Auth.Login> {
                LoginScreen(
                    onLoginClick = {
                        // Set login result if needed
                        resultStore.setResult("auth_result", "logged_in")
                        onNavigateToApp()
                    },
                    onNavigateToRegister = {
                        navigator.navigate(Destination.Auth.Register)
                    }
                )
            }
            entry<Destination.Auth.Register> {
                RegisterScreen(
                    onRegisterClick = {
                        // Set register result if needed
                        resultStore.setResult("auth_result", "registered")
                        onNavigateToApp()
                    },
                    onNavigateToLogin = {
                        navigator.navigate(Destination.Auth.Login)
                    }
                )
            }
        }
    )
}
