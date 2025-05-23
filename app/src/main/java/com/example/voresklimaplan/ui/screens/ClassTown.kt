package com.example.voresklimaplan.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.voresklimaplan.R
import com.example.voresklimaplan.common.TextFontGaming
import com.example.voresklimaplan.ui.viewModel.ClassesViewModel
import com.example.voresklimaplan.ui.viewModel.GameViewModel
import kotlin.random.Random


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun SpawnTownLevelImages() {
    val gameViewModel: GameViewModel = viewModel() //instans af gameViewModel
    val townScore: Int =
        gameViewModel.game.score //skal nok i virkeligheden hente en mustableStateOf(Int) form for score fra viewmodel
    //val level = townScore / 100
    val level = 6

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        )
        {
            val screenWidth = constraints.maxWidth
            val screenHeight = constraints.maxHeight

            repeat(level) {
            //den nederst halvdel af skærmen er græs, så alle tingene skal placeres random nederste halvdel
            val randomX = Random.nextInt(0, screenWidth)
            val randomY = Random.nextInt(screenHeight / 2, screenHeight)

            Image(
                painter = painterResource(R.drawable.game_windmill),
                contentDescription = null,
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = randomX,
                            y = randomY
                        )
                    }
            )
        }
    }
}


@Composable
fun ClassTown(
    navController: NavHostController,
    viewModel: ClassesViewModel
) {
    val classList = viewModel.classList //Henter listen fra ViewModel...

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    )
    {
        //baggrundsbillede
        Image(
            painter = painterResource(R.drawable.background_start_game),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

    }
        Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
        ) {
            //vi henter fra den første klasse i listen
            TextFontGaming(classList[0].className, 20, align = TextAlign.Center)
            TextFontGaming(classList[0].score.toString(), 10, align = TextAlign.Center)
        }

        SpawnTownLevelImages()

}





@Composable
fun SpawnImgFromScore(
    score: Int //skal kaldes med classList[0].score
) {

}