package com.example.mandatoryassignment


sealed class NavRoutes(val route: String) {
    data object ListViewScreen : NavRoutes("list")
    data object LogInScreen : NavRoutes("login")
    data object CreateFriendScreen : NavRoutes("create")
    data object UpdateFriendScreen : NavRoutes("update")
}