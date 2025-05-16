package com.example.voresklimaplan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import com.example.voresklimaplan.data.FirestoreRepository
import com.example.voresklimaplan.ui.theme.VoresKlimaplanTheme
import com.example.voresklimaplan.ui.viewModel.ClassesViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val classesViewModel = ClassesViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            classesViewModel.getAllClasses()
        }

        setContent {
            VoresKlimaplanTheme {
                Scoreboard(classesViewModel = classesViewModel)
            }
        }
    }
}

@Composable
fun Scoreboard(classesViewModel: ClassesViewModel) {

}

suspend fun getClasses() {
    val firestoreRepository = FirestoreRepository()
    println(firestoreRepository.getClassroom())
}
