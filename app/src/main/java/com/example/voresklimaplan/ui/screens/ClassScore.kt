package com.example.voresklimaplan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.voresklimaplan.R
import com.example.voresklimaplan.common.TextFontGaming


@Composable
fun ScoreboardScreen (navController: NavHostController) { //Forklar
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
            Scoreboard()
            ScoreboardMenu(navController) //Forklar
        }
    }
}

@Composable
fun Scoreboard () {
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
        Column (
            modifier = Modifier
                .height(130.dp)
        ) {
            TextFontGaming("SCOREBOARD", fontSizeInput = 28)
        }
    }
}

@Composable
fun ScoreboardMenu (navController: NavHostController) { //Forklar
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
                //navController.navigate("KlassensByScreen") Mangler
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



@Preview(showBackground = true)
@Composable
fun ScoreboardScreenPreview() {
    val navController = rememberNavController()
    ScoreboardScreen(navController = navController) //??
}
