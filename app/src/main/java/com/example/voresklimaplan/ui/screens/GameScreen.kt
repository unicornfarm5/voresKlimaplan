package com.example.voresklimaplan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.voresklimaplan.R
import com.example.voresklimaplan.ui.common.TextFontGaming
import com.example.voresklimaplan.ui.viewModel.ClassesViewModel


@Composable
fun GameScreen(
    navController: NavController,
    viewModel: ClassesViewModel
) {
    val classList = viewModel.classList //Henter listen fra ViewModel...

    TextFontGaming("Davs her skal spillet være", 20)
    //vi viser ikke en opdaterende score under spillet i mvp
    //TextFontGaming(classList[0].score.toString(), 20,  align = TextAlign.Center)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.background_game),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        //hapser alt der er fra spillet?
        //spørg benjamin om han syntes der skal være en ui-screen til game eller om alt skal være under game

    }
}