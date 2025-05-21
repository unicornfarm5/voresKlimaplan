package com.example.voresklimaplan.game

import android.media.Image
import androidx.compose.animation.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.voresklimaplan.R
import com.example.voresklimaplan.common.TextFontGaming
import com.example.voresklimaplan.game.domain.Game
import com.example.voresklimaplan.game.domain.MoveDirection
import com.example.voresklimaplan.game.util.detectMoveGesture
import com.example.voresklimaplan.ui.viewModel.ClassesViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: ClassesViewModel
) {
    val context = LocalContext.current  // Her henter vi Context
    val classList = viewModel.classList
    val game = remember { Game() }
    var moveDirection by remember { mutableStateOf(MoveDirection.None) }

    // Hent billedet som ImageBitmap til Canvas
    val earthImageBitmap = ImageBitmap.imageResource(context.resources, R.drawable.game_earth)
    val earthWidth = earthImageBitmap.width
    val earthHeight = earthImageBitmap.height

    var screenWidth by remember { mutableStateOf(0) }
    var screenHeight by remember { mutableStateOf(0) }

    val earthOffsetX = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    val scope = rememberCoroutineScope()

    Column {
        TextFontGaming("Davs her er et spil", 20)
        TextFontGaming(classList[0].score.toString(), 20, align = TextAlign.Center)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    screenWidth = it.size.width
                    screenHeight = it.size.height

                    // Startposition: midt i bunden
                    if (earthOffsetX.value == 0f) {
                        scope.launch {
                            earthOffsetX.snapTo(screenWidth / 2f - earthWidth / 2f)
                        }
                    }
                }
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        detectMoveGesture(
                            gameStatus = game.status,
                            onLeft = {
                                scope.launch {
                                    val newX = (earthOffsetX.value - 15f)
                                        .coerceAtLeast(0f)
                                    earthOffsetX.snapTo(newX)
                                }
                            },
                            onRight = {
                                scope.launch {
                                    val newX = (earthOffsetX.value + 15f)
                                        .coerceAtMost(screenWidth - earthWidth.toFloat())
                                    earthOffsetX.snapTo(newX)
                                }
                            },
                            onFingerLifted = {
                                moveDirection = MoveDirection.None
                            }
                        )
                    }
                }
        ) {
            // Baggrund
            Image(
                painter = painterResource(R.drawable.background_game),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Jordkloden tegnes
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawImage(
                    image = earthImageBitmap,
                    topLeft = Offset(
                        x = earthOffsetX.value,
                        y = size.height - earthHeight // placer i bunden
                    )
                )
            }
        }
    }
}



/*
//fra https://www.youtube.com/watch?v=LXZw2RyV06s&t=849s

//Føen
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: ClassesViewModel
) {
    val context = LocalContext.current
    val classList = viewModel.classList //Henter listen fra ViewModel...
    val game = remember { Game() }
    var moveDirection by remember { mutableStateOf(MoveDirection.None) }

    // Hent billedet som ImageBitmap til Canvas
    val earthImageBitmap = ImageBitmap.imageResource(context.resources, R.drawable.game_earth)
    val earthWidth = earthImageBitmap.width
    val earthHeight = earthImageBitmap.height

    var screenWidth by remember { mutableIntStateOf(0) }
    var screenHeight by remember { mutableIntStateOf(0) }

    val earthOffsetX = remember {
        androidx.compose.animation.core.Animatable(0f) //todo skal ændres
    }

    val scope = rememberCoroutineScope()


    Column {
        TextFontGaming("Davs her er et spil", 20)
        TextFontGaming(classList[0].score.toString(), 20, align = TextAlign.Center)

    }
}

/*
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit){ // lytter efter fingerbevægele
                awaitPointerEventScope {
                    detectMoveGesture( //kalder detectMoveGesture
                        gameStatus = game.status,
                        onLeft = {}, //  Er tomme gør ingenting endnu
                        onRight = {},
                        onFingerLifted = {}
                    )
                }
            }
    ) {
        Image( //background img
            painter = painterResource(R.drawable.background_game),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


    }
}

 */

 */

