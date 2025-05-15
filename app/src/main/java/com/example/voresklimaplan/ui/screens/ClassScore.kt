package com.example.voresklimaplan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voresklimaplan.R


@Composable
fun ScoreboardScreen () {
    Box(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        Image(painter = painterResource(R.drawable.gamebackground),
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
        Text(
            text = "Scoreboard",
            fontSize = 32.sp,
            modifier = Modifier
                .height(140.dp)
        )
    }
}

@Composable
fun ScoreboardMenu () {

}

@Preview(showBackground = true)
@Composable
fun BackgroundScreenPreview() {
    ScoreboardScreen()
}