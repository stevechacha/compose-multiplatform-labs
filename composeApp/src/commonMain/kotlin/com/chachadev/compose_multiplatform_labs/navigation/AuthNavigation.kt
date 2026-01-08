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
import com.chachadev.compose_multiplatform_labs.screens.LoginScreen
import com.chachadev.compose_multiplatform_labs.screens.RegisterScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun AuthNavigation(
    modifier: Modifier = Modifier,
    onNavigateToApp: () -> Unit = {}
) {
    val authBackStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Destination.Auth.Login::class, Destination.Auth.Login.serializer())
                    subclass(Destination.Auth.Register::class, Destination.Auth.Register.serializer())
                }
            }
        },
        Destination.Auth.Login
    )

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
                    onLoginClick = onNavigateToApp,
                    onNavigateToRegister = {
                        authBackStack.add(Destination.Auth.Register)
                    }
                )
            }
            entry<Destination.Auth.Register> {
                RegisterScreen(
                    onRegisterClick = onNavigateToApp,
                    onNavigateToLogin = {
                        authBackStack.add(Destination.Auth.Login)
                    }
                )
            }
        }
    )
}
