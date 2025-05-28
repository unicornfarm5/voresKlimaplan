package com.example.voresklimaplan.ui.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.voresklimaplan.ui.viewModel.GameViewModel

@Composable
//start over screen - meget lig gameLandingPage
//Linea
fun GameOverScreen(
    navController: NavController,
    gameViewModel: GameViewModel
) {
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
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "GAME\n\nOVER",
                fontSize = 50.sp,
                textAlign = TextAlign.Center,
                fontFamily = PressStartFont
            )
            Spacer(modifier = Modifier.height(40.dp))

            PurpleButton(
                buttonTekst = "SPIL IGEN \uD83D\uDCCB",
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

            Image(
                painter = painterResource(R.drawable.game_earth_angryy),
                contentDescription = null,
                modifier = Modifier,
                contentScale = ContentScale.Crop
            )

        }
    }
}
