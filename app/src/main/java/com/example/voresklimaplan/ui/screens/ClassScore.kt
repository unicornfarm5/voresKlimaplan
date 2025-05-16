package com.example.voresklimaplan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voresklimaplan.R
import com.example.voresklimaplan.common.TextFontGaming


@Composable
fun ScoreboardScreen () {
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
                painter = painterResource(id = R.drawable.image113),
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
fun ScoreboardMenu () {
Box (
    modifier = Modifier
        .height(50.dp)
)

}

@Preview(showBackground = true)
@Composable
fun ScoreboardScreenPreview() {
    ScoreboardScreen()
}
