package com.example.voresklimaplan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.voresklimaplan.navigation.Navhost
import com.example.voresklimaplan.ui.theme.VoresKlimaplanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VoresKlimaplanTheme {
                val navController = rememberNavController()
                Navhost(navController = navController)
            }
        }
    }
}
