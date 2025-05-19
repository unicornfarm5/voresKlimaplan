package com.example.voresklimaplan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.voresklimaplan.R
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.voresklimaplan.common.TextFontBubbles
import com.example.voresklimaplan.common.TextFontGaming
import com.example.voresklimaplan.ui.viewModel.ClassesViewModel


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
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column() {
            //vi henter fra den første klasse i listen
            TextFontGaming(classList[0].className, 30, align = TextAlign.Center)
            TextFontGaming(classList[0].score.toString(), 20,  align = TextAlign.Center)

        }
    }
}


@Composable
fun SpawnImgFromScore(
    score: Int //skal kaldes med classList[0].score
) {

}