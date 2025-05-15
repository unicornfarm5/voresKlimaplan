package com.example.voresklimaplan.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.voresklimaplan.LandingPage


@Composable
fun Navhost(navController: NavHostController) {
    //Linea
    NavHost(navController = navController, startDestination = "LandingPage") {
        composable("LandingPage") {
            LandingPage(
                navController = navController
            )
        }
    }
}
