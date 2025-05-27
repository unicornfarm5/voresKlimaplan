package com.example.voresklimaplan.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.voresklimaplan.game.MainScreen
import com.example.voresklimaplan.ui.screens.BackgroundScreen
import com.example.voresklimaplan.ui.screens.ClassTown
import com.example.voresklimaplan.ui.screens.GameLandingPage
import com.example.voresklimaplan.ui.screens.GameOverScreen
import com.example.voresklimaplan.ui.screens.ScoreboardScreen
import com.example.voresklimaplan.ui.viewModel.ClassesViewModel
import com.example.voresklimaplan.ui.viewModel.GameViewModel

@SuppressLint("ViewModelConstructorInComposable")
@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
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

        composable("ScoreboardScreen") { //???
            ScoreboardScreen(
                navController = navController,
                viewModel = classesViewModel,
                gameViewModel = gameViewModel
            )
        }

        composable("ClassTown") { //???
            ClassTown(
                navController = navController,
                viewModel = classesViewModel
            )
        }

        composable("GameOverScreen") { //???
            GameOverScreen(
                navController = navController,
                gameViewModel = gameViewModel
            )
        }
    }
}
