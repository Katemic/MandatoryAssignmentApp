package com.example.mandatoryassignment


sealed class NavRoutes(val route: String) {
    data object ListViewScreen : NavRoutes("list")
}