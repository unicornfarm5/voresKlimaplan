package com.example.voresklimaplan.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.voresklimaplan.ui.screens.BackgroundScreen
import com.example.voresklimaplan.ui.screens.GameLandingPage
import com.example.voresklimaplan.ui.screens.Game

@Composable
fun Navhost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "LearningPage") {
        composable("LearningPage") {
            BackgroundScreen(navController = navController)
        }

        composable("GameLandingPage") {
            GameLandingPage(navController = navController)
        }

        composable("GameScreen") {
            Game(navController = navController) // hvis du har en sådan funktion
        }
    }
}
