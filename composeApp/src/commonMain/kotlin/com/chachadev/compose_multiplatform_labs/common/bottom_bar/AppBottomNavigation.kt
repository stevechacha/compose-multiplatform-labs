package com.chachadev.compose_multiplatform_labs.common.bottom_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.chachadev.compose_multiplatform_labs.navigation.Destination

@Composable
fun AppBottomNavigation(
    selectedKey: NavKey,
    onSelectedKey: (NavKey) -> Unit,
){
    BottomAppBar {
        bottomNavItems.forEach { (destination,data) ->
            NavigationBarItem(
                selected = destination == selectedKey,
                onClick = {
                    onSelectedKey(destination)
                },
                icon = {
                    Icon(
                        imageVector = data.drawableIcon,
                        contentDescription = data.title
                    )
                },
                label = {
                    Text(text = data.title)
                }
            )
        }
    }
}



