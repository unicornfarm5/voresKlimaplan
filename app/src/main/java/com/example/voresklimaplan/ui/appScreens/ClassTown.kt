package com.example.voresklimaplan.ui.appScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.voresklimaplan.R
import com.example.voresklimaplan.ui.common.TextFontGaming
import com.example.voresklimaplan.ui.viewModel.ClassesViewModel
import com.example.voresklimaplan.ui.viewModel.GameViewModel
import kotlin.random.Random

//Linea

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun SpawnTownLevelImages(
    classesViewModel: ClassesViewModel,
    gameViewModel: GameViewModel
) {
    val townScore: Int = classesViewModel.classList[0].score
    val level = townScore / 100

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        )
        {
            repeat(level) {
            //den nederst halvdel af skærmen er græs, så alle tingene skal placeres random nederste halvdel
            val randomX = Random.nextInt(0, gameViewModel.screenWidth)
            val randomY = Random.nextInt(0, gameViewModel.screenHeight / 2)
            val randomX2 = Random.nextInt(0, gameViewModel.screenWidth)
            val randomY2 = Random.nextInt(0, gameViewModel.screenHeight / 2)

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
                Image(
                    painter = painterResource(R.drawable.game_house_car),
                    contentDescription = null,
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                x = randomX2,
                                y = randomY2
                            )
                        }
                )
        }
    }
}


@Composable
fun ClassTown(
    navController: NavHostController,
    viewModel: ClassesViewModel,
    gameViewModel: GameViewModel
) {
    val classList = viewModel.classList
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

        SpawnTownLevelImages(viewModel, gameViewModel)
    }
        Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            //vi henter fra den første klasse i listen
            TextFontGaming(classList[0].className, 20, align = TextAlign.Center)
            TextFontGaming(classList[0].score.toString(), 17, align = TextAlign.Center)
        }

}


