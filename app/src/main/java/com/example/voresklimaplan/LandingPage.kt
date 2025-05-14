package com.example.voresklimaplan

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.voresklimaplan.common.PurpleButton

@Composable
//start game screen
fun LandingPage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        Image(painter = painterResource(R.drawable.gamebackground),
            contentDescription = null,
            modifier = Modifier.fillMaxSize())
    }

    Column {
        Text("Save the earth")
        PurpleButton("Spil") //jeg ved ikke hvordan man får emoji ind
    }

}