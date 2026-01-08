package com.chachadev.compose_multiplatform_labs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chachadev.compose_multiplatform_labs.navigation.RootNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        RootNavigation(
            modifier = Modifier.fillMaxSize()
        )
    }
}