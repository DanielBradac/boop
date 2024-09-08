package cz.bradacd.boop.ui.navigation

enum class Screens(val url: String) {
    HomeScreen("home"),
    BoopScreen("details/{boopName}")
}