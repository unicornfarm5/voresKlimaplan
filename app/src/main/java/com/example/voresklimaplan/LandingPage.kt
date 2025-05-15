package com.example.voresklimaplan

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.voresklimaplan.common.PurpleButton

@Composable
//start game screen
//Linea
fun LandingPage(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    )
    {   //baggrundsbilled
        Image(painter = painterResource(R.drawable.gamebackground),
            contentDescription = null,
            modifier = Modifier.fillMaxSize())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
            verticalArrangement = Arrangement.Center, //afstand mellem elementerne
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Save the earth", fontSize = 50.sp)
        PurpleButton("Spil") //jeg ved ikke hvordan man får emoji iiind
    }

}