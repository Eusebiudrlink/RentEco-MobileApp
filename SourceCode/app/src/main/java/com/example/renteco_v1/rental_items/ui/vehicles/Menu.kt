package com.example.renteco_v1.rental_items.ui.vehicles

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.renteco_v1.Screen

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null,
    val route: String
)

class Menu {
    companion object {
        val items = listOf(
            NavigationItem(
                title = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                route = Screen.VehicleListScreen.route
            ),
            NavigationItem(
                title = "Account",
                selectedIcon = Icons.Filled.AccountBox,
                unselectedIcon = Icons.Outlined.AccountBox,
                route = Screen.Account.route
            ),
            NavigationItem(
                title = "Rides history",
                selectedIcon = Icons.Filled.DateRange,
                unselectedIcon = Icons.Outlined.DateRange,
                route = Screen.RidesScreen.route
            ),
            NavigationItem(
                title = "Contact",
                selectedIcon = Icons.Filled.Info,
                unselectedIcon = Icons.Outlined.Info,
                route = Screen.ContactScreen.route
            ),
            NavigationItem(
                title = "Log out",
                selectedIcon = Icons.Filled.ExitToApp,
                unselectedIcon = Icons.Outlined.ExitToApp,
                route = Screen.VehicleListScreen.route
            ),
        )
    }

}
