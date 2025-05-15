package com.example.voresklimaplan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.voresklimaplan.data.FirestoreRepository
import com.example.voresklimaplan.ui.theme.VoresKlimaplanTheme
import com.example.voresklimaplan.ui.viewModel.ClassesViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VoresKlimaplanTheme {
                Scoreboard(classesViewModel = ClassesViewModel())
            }
        }
    }
}

@Composable
fun Scoreboard(classesViewModel: ClassesViewModel) {
    classesViewModel.getAllClasses()
}


suspend fun getClasses() {
    val getClassroom = FirestoreRepository()
    println(getClassroom.getClassroom())
}