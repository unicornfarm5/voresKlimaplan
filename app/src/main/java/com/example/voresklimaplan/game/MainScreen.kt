package com.example.voresklimaplan.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.voresklimaplan.R
import com.example.voresklimaplan.game.domain.MoveDirection
import com.example.voresklimaplan.game.util.detectMoveGesture
import com.example.voresklimaplan.ui.viewModel.ClassesViewModel
import com.example.voresklimaplan.ui.viewModel.GameViewModel
import kotlinx.coroutines.launch

//Føen og Jonas
@Composable
fun MainScreen(navController: NavController, viewModel: ClassesViewModel) {
    val context = LocalContext.current  // Her henter vi Context
    val gameViewModel: GameViewModel = viewModel() //instans af gameViewModel
    val classList = viewModel.classList
    val density = LocalDensity.current.density

    // Hent billedet som ImageBitmap til Canvas
    val earthImageBitmap = ImageBitmap.imageResource(R.drawable.game_earth)
    val earthWidth = earthImageBitmap.width
    val earthHeight = earthImageBitmap.height

    //Sender værdierne over til viewModel
    gameViewModel.earthWidth = earthWidth
    gameViewModel.earthHeight = earthHeight


    val scope = rememberCoroutineScope()


    DisposableEffect(Unit) { //Hvis spillet lukkes stoppes spillet
        onDispose {
            gameViewModel.stopGame()
         }
    }
    BackHandler { //Sørger for at spillet lukkes korrekt hvis der trykkes tilbage
        gameViewModel.stopGame()
        navController.popBackStack()
    }

    //Linea
    //starter spillet automatisk når game-siden åbnes
    LaunchedEffect(Unit) {
        //delay(500) // Vent evt. lidt, så layout er klar
        gameViewModel.startGame(density)
    }

    //Jonas
    Column {
        Text(
            text = "Score: ${gameViewModel.score}",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(top = 16.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    gameViewModel.screenWidth = it.size.width
                    gameViewModel.screenHeight = it.size.height

                    // Startposition: midt i bunden
                    if (!gameViewModel.hasCenteredEarth) {
                        scope.launch {
                            gameViewModel.earthOffsetX.snapTo(gameViewModel.screenWidth / 2f - earthWidth / 2f)
                            gameViewModel.hasCenteredEarth = true
                        }
                    }
                }
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        detectMoveGesture(
                            gameStatus = gameViewModel.game.status,
                            onMove = { deltaX -> //Ny kode som gør glode mere smooth
                                scope.launch {
                                    val newX = (gameViewModel.earthOffsetX.value + deltaX)
                                        .coerceIn(0f, gameViewModel.screenWidth - earthWidth.toFloat())

                                    gameViewModel.earthOffsetX.snapTo(newX)
                                }
                            },
                            onFingerLifted = {
                                gameViewModel.moveDirection = MoveDirection.None
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
                        x = gameViewModel.earthOffsetX.value,
                        y = size.height - earthHeight // placer i bunden
                    )
                )
            }

            //Linea
            //Falling GameTargets
            gameViewModel.activeGameTargets.forEach { fallingGameTarget ->
                val xDp = with(LocalDensity.current) { fallingGameTarget.xCordinate.toDp() }
                val yDp = with(LocalDensity.current) { fallingGameTarget.yCordinate.toDp() }

                Image(
                    painter = painterResource(id = fallingGameTarget.imageId),
                    contentDescription = fallingGameTarget.targetName,
                    modifier = Modifier
                        .size(150.dp)
                            .offset(x = xDp, y = yDp)
                )
            }
        }
    }
}
//fra https://www.youtube.com/watch?v=LXZw2RyV06s&t=849s