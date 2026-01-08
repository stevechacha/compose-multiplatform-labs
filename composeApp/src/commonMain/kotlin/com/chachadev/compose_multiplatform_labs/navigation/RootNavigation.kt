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
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun RootNavigation(
    modifier: Modifier = Modifier
){
    val rootBackStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Destination.Auth::class, Destination.Auth.serializer())
                    subclass(Destination.App::class, Destination.App.serializer())
                }
            }
        },
        Destination.Auth
    )

    NavDisplay(
        backStack = rootBackStack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Destination.Auth> {
                AuthNavigation(
                    onNavigateToApp = {
                        rootBackStack.remove(Destination.Auth)
                        rootBackStack.add(Destination.App)
                    }
                )
            }
            entry<Destination.App> {
                AppNavigation()
            }
        }
    )
}