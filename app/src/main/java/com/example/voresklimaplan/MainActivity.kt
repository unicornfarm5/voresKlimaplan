package com.example.voresklimaplan

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.voresklimaplan.data.FirestoreRepository
import com.example.voresklimaplan.navigation.Navhost
import com.example.voresklimaplan.ui.theme.VoresKlimaplanTheme
import com.example.voresklimaplan.ui.viewModel.ClassesViewModel
import com.example.voresklimaplan.ui.viewModel.GameViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VoresKlimaplanTheme {
                val classesViewModel: ClassesViewModel = viewModel()
                val gameViewModel: GameViewModel = viewModel()
                val navController = rememberNavController()
                Navhost(navController = navController, classesViewModel = classesViewModel, gameViewModel = gameViewModel)
            }
        }
    }
}
