package com.chachadev.compose_multiplatform_labs

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Composemultiplatformlabs",
    ) {
        App()
    }
}