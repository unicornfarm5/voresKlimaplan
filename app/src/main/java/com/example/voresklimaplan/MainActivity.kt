package com.example.voresklimaplan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.voresklimaplan.data.FirestoreRepository
import com.example.voresklimaplan.navigation.Navhost
import com.example.voresklimaplan.ui.theme.VoresKlimaplanTheme
import com.example.voresklimaplan.ui.viewModel.ClassesViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VoresKlimaplanTheme {
                val classesViewModel: ClassesViewModel = viewModel() //problems hvis man fx drejer tlf og vender skærm //pointe rapport vi ændr den her

                val navController = rememberNavController() //??
                Navhost(navController = navController, classesViewModel = classesViewModel) //??
            }
        }
    }
}

/*
@Composable
fun Scoreboard(classesViewModel: ClassesViewModel) {
    classesViewModel.getAllClasses()
}

suspend fun getClasses() {
    val getClassroom = FirestoreRepository()
    println(getClassroom.getClassroom())
}
 */
