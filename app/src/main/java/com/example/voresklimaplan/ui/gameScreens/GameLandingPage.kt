package com.example.voresklimaplan.ui.gameScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.voresklimaplan.R
import com.example.voresklimaplan.ui.common.PurpleButton
import com.example.voresklimaplan.ui.common.TextFontBubbles

@Composable
//start game screen
//Føen
fun GameLandingPage(navController: NavController) {
    val RubikBubbles = FontFamily(
        Font(R.font.rubikbubbles, weight = FontWeight.Normal)
    )
    val PressStartFont = FontFamily(
        Font(R.font.pressstart, weight = FontWeight.Normal)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background picture
        Image(
            painter = painterResource(R.drawable.background_start_game),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // The Title
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextFontBubbles(
                textInput = "SAVE\nTHE EARTH",
                fontSizeInput = 70
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "RAM ALLE DE TING SOM\n ER GODT FOR KLODEN",
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F5229),
                fontFamily = PressStartFont
            )

            Spacer(modifier = Modifier.height(40.dp))

            PurpleButton(
                buttonTekst = "\uD83C\uDFAE  SPIL  \uD83C\uDFAE",
                fontFamily = PressStartFont,
                fontSize = 18,
                navController = navController,
                navigateTo = "MainScreen"
            )
//Linea
            PurpleButton(
                buttonTekst = "SCOREBOARD \uD83D\uDCCB",
                fontFamily = PressStartFont,
                fontSize = 18,
                navController = navController,
                navigateTo = "ScoreboardScreen"
            )

        }
    }
}