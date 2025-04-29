package com.anri0145.assesment2.navigation

sealed class Screen (val route: String) {
    data object Home: Screen ("mainScreen")
}