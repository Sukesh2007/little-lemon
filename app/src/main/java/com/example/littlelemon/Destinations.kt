package com.example.littlelemon

interface Destinations{
    val route: String
}

object Home: Destinations{
    override val route = "home"
}

object OnBoarding: Destinations{
    override val route = "onBoarding"
}

object Profile: Destinations{
    override val route = "profile"
}