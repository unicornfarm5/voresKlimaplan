package com.example.voresklimaplan.game

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.voresklimaplan.R
import com.example.voresklimaplan.common.TextFontGaming
import com.example.voresklimaplan.game.domain.Game
import com.example.voresklimaplan.game.domain.MoveDirection
import com.example.voresklimaplan.game.util.detectMoveGesture
import com.example.voresklimaplan.ui.viewModel.ClassesViewModel


//Føen
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: ClassesViewModel
) {
    val classList = viewModel.classList //Henter listen fra ViewModel...
    val game = remember { Game() }
    var moveDirection by remember{mutableStateOf(MoveDirection.None) }


    TextFontGaming("Davs her er et spil", 20)
    TextFontGaming(classList[0].score.toString(), 20,  align = TextAlign.Center)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit){
                awaitPointerEventScope {
                    detectMoveGesture(
                        gameStatus = game.status,
                        onLeft = {},
                        onRight = {},
                        onFingerLifted = {}
                    )
                }
            }
    ) {
        Image(
            painter = painterResource(R.drawable.background_game),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


    }
}

