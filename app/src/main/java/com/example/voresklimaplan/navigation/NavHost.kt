package com.example.voresklimaplan.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.voresklimaplan.ui.gameScreens.MainScreen
import com.example.voresklimaplan.ui.appScreens.BackgroundScreen
import com.example.voresklimaplan.ui.appScreens.ClassTown
import com.example.voresklimaplan.ui.gameScreens.GameLandingPage
import com.example.voresklimaplan.ui.gameScreens.GameOverScreen
import com.example.voresklimaplan.ui.appScreens.ScoreboardScreen
import com.example.voresklimaplan.ui.viewModel.ClassesViewModel
import com.example.voresklimaplan.ui.viewModel.GameViewModel

//Føen, Jonas, Linea og Nikoleta

@Composable
fun Navhost(navController: NavHostController, classesViewModel: ClassesViewModel,gameViewModel: GameViewModel) {

    NavHost(navController = navController, startDestination = "LearningPage") {
        composable("LearningPage") {
            BackgroundScreen(navController = navController)
        }

        composable("GameLandingPage") {
            GameLandingPage(navController = navController)
        }

        composable("MainScreen") {
            MainScreen(
                navController = navController,
                viewModel = classesViewModel,
                gameViewModel = gameViewModel
            )
        }

        composable("ScoreboardScreen") {
            ScoreboardScreen(
                navController = navController,
                viewModel = classesViewModel,
            )
        }

        composable("ClassTown") {
            ClassTown(
                navController = navController,
                viewModel = classesViewModel,
                gameViewModel = gameViewModel
            )
        }

        composable("GameOverScreen") {
            GameOverScreen(
                navController = navController
            )
        }
    }
}
