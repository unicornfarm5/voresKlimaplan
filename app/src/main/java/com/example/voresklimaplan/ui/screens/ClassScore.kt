package com.example.voresklimaplan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.voresklimaplan.R
import com.example.voresklimaplan.common.TextFontGaming
import com.example.voresklimaplan.ui.viewModel.ClassesViewModel


@Composable
fun ScoreboardScreen (navController: NavHostController, viewModel: ClassesViewModel) { //Forklar
    val classList = viewModel.classList //Henter listen fra Firestore via ViewModel

    LaunchedEffect(Unit) { //??
        viewModel.getAllClasses()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    )
    //Baggrunden
    {
        Image(painter = painterResource(R.drawable.background_start_game),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        //UI-indhold som ligger ovenpå baggrund
        Column () {
            val scoreboardClasses = classList.map { it.className } //Her oprettes en ny liste ud fra classList bare kun med className
            Scoreboard(scoreboardClasses = scoreboardClasses)

            ScoreboardMenu(navController)
        }
    }
}

@Composable
fun Scoreboard (scoreboardClasses: List<String>) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        contentAlignment = Alignment.Center
    ) {
            Image(
                painter = painterResource(id = R.drawable.scoreboard_wood),
                contentDescription = "Scoreboard",
                modifier = Modifier
                    .fillMaxSize()

            )
        Column (                            //todo Husk at gøre så navne automatisk passer ind i scoreboard!
            modifier = Modifier
                .offset(x = (5).dp, y = (20).dp)
                .height(200.dp)
        ) {
            TextFontGaming("\uD83C\uDFC6 SCOREBOARD \uD83C\uDFC6", fontSizeInput = 24)
            Column (
                modifier = Modifier
                    .offset(x = (20).dp, y = (20).dp)
            )
            {
                scoreboardClasses.forEach { scoreboardClass ->
                    TextFontGaming(scoreboardClass, fontSizeInput = 11)
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }

    }
}

@Composable
fun ScoreboardMenu (navController: NavHostController) {
Box (
    modifier = Modifier
        .fillMaxWidth()
        .height(320.dp)
        .offset(x = (-60).dp, y = (150).dp) //Sætter placering af menu
) {
    Image(
        painter = painterResource(id = R.drawable.scoreboard_menu),
        contentDescription = "Scoreboard menu",
        modifier = Modifier
            .fillMaxSize()
    )
    Box (
        modifier = Modifier
            .offset(x = (130).dp, y = (40).dp)
            .clickable {
                //todo navController.navigate("KlassensByScreen") Mangler
                println ("Klassen by knap er klikket")
            }
    ) {
        TextFontGaming("KLASSENS BY", fontSizeInput = 14)
    }
    Box(
        modifier = Modifier
            .offset(x = (150).dp, y = (100).dp)
            .clickable {
                navController.navigate("BackgroundScreen") //Forside
                println ("Forside knap er klikket")
            }

    ) {
        TextFontGaming("FORSIDE", fontSizeInput = 14)
    }
    Box(
        modifier = Modifier
            .offset(x = (140).dp, y = (160).dp)
            .clickable {
                navController.navigate("GameLandingPage")
                println ("Spil igen knap er klikket")
            }
    ) {
        TextFontGaming("SPIL IGEN", fontSizeInput = 14)
    }
}
}

/*
@Preview(showBackground = true)
@Composable
fun ScoreboardScreenPreview() {
    val navController = rememberNavController()

    ScoreboardScreen(navController = navController) //??
}
 */