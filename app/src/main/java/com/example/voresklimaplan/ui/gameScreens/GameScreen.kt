package com.example.voresklimaplan.ui.gameScreens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.voresklimaplan.R
import com.example.voresklimaplan.game.domain.model.GameStatus
import com.example.voresklimaplan.game.domain.model.MoveDirection
import com.example.voresklimaplan.game.util.detectMoveGesture
import com.example.voresklimaplan.ui.viewModel.ClassesViewModel
import com.example.voresklimaplan.ui.viewModel.GameViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.example.voresklimaplan.ui.common.pressStartFont
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

//Føen og Jonas

@Composable
fun MainScreen(navController: NavController, viewModel: ClassesViewModel, gameViewModel: GameViewModel) {
    val density = LocalDensity.current.density
    val tick = remember { mutableStateOf(0L) }
    val TARGET_SIZE_DP = 80.dp // så man bruger samme str
    val TARGET_SIZE_PX = with(LocalDensity.current) { TARGET_SIZE_DP.toPx() }
    gameViewModel.targetSizePx = TARGET_SIZE_PX
    val scope = rememberCoroutineScope()

    // Hent billedet som ImageBitmap til Canvas
    val earthImage = ImageBitmap.imageResource(R.drawable.game_earth)
    val earthWidth = earthImage.width
    val earthHeight = earthImage.height

// todo: Chatten har lavet denne kode
//laver target billeder om til imagebitmap som er mindre krævende at rendere
    gameViewModel.gameTargets.forEach { target ->
        if (!gameViewModel.imageCache.containsKey(target.imageId)) {
            val bm: ImageBitmap? = runCatching {
                ImageBitmap.imageResource(id = target.imageId)
            }.onFailure { e ->
                Log.w(
                    "GameImages",
                    "Kunne ikke loade “${target.targetName}” (id=${target.imageId}): ${e.message}"
                )
            }.getOrNull()

            // Hvis load lykkedes, gem bitmap i cache
            bm?.let { gameViewModel.imageCache[target.imageId] = it }
        }
    }

//Sender værdier over til viewModel
    gameViewModel.earthWidth = earthWidth
    gameViewModel.earthHeight = earthHeight


    LaunchedEffect(Unit) {
        gameViewModel.startGame(density)
    }

//Jonas
    DisposableEffect(Unit) { //Hvis spillet lukkes stoppes spillet
        onDispose {
            gameViewModel.stopGame({})
         }
    }

    BackHandler { //Sørger for at spillet lukkes korrekt hvis der trykkes tilbage
        gameViewModel.stopGame({})
        navController.popBackStack()
    }

    //Føen
    // todo: chatten brugt her
    // Spillet venter til layout før den starter - ryk til viewmodel
    LaunchedEffect(gameViewModel.layoutReady) {
        println("layoutReady = $gameViewModel.layoutReady, game status = ${gameViewModel.game.status}")
        if (gameViewModel.layoutReady && gameViewModel.game.status != GameStatus.Started) {
            println(" startGame kaldt")
            gameViewModel.startGame(density)
        }
    }

//Føen
    // todo: Chatten brugt her
    DisposableEffect(Unit) { //  ryk til game
        val job = scope.launch {
            var lastFrameTime = System.nanoTime()
            while (gameViewModel.game.status != GameStatus.Over && isActive) {
                val now = withFrameNanos { it }
                val deltaTimeMillis = (now - lastFrameTime) / 1_000_000L
                lastFrameTime = now

                // odaterer kun targets, hvis spillet stadig er aktivt
                if (gameViewModel.game.status == GameStatus.Started) {
                    gameViewModel.updateTargetPosition(deltaTimeMillis, onGameOver = {
                        println("Navigating to GameOverScreen")
                        viewModel.saveScoreInFireBase("BJYAEk0hrL0s0WipYngc", gameViewModel.score)
                        navController.navigate("GameOverScreen")
                    })
                }
                // gør at der hele tiden er en ændring --> gør det muligt for spillet at køre smooth
                tick.value = now

               // Stopper loopet, hvis spillet er slut
                delay(20L)
            }
        }
        onDispose { job.cancel() }
    }

    //Jonas
    // todo: chatten brugt under
    Column {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    gameViewModel.screenWidth = it.size.width
                    gameViewModel.screenHeight = it.size.height

                    // Startposition: midt i bunden. Snapto og scope er del af UI
                    if (!gameViewModel.hasCenteredEarth) {
                        scope.launch {
                            gameViewModel.earthOffsetX.snapTo(gameViewModel.screenWidth / 2f - earthWidth / 2f)
                            gameViewModel.hasCenteredEarth = true
                        }
                    }
                    gameViewModel.layoutReady = true
                }
                .pointerInput(Unit) { // ui relateret
                    awaitPointerEventScope {
                        detectMoveGesture(
                            gameStatus = gameViewModel.game.status,
                            onMove = { deltaX -> //Ny kode som gør globe mere smooth
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

            //Linea
            // Baggrund
            Image(
                painter = painterResource(R.drawable.background_game),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Score: ${gameViewModel.score}",
                fontSize = 24.sp,
                fontFamily = pressStartFont,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 16.dp)
            )


            // Jordkloden tegnes
            //Føen
            // todo: chatten brugt under
            Canvas(modifier = Modifier
                .fillMaxSize()
                .then(Modifier.drawBehind { tick.value }))
            {
                gameViewModel.activeGameTargets.forEach { target ->
                    // cachet billeder med ImageBitmap
                    val bitmap = gameViewModel.imageCache[target.imageId]
                    if (bitmap != null) {
                        drawImage(
                            image = bitmap,
                            srcOffset = IntOffset(0, 0),
                            srcSize = IntSize(bitmap.width, bitmap.height),
                            dstOffset = IntOffset(target.xCordinate.toInt(), target.yCordinate.toInt()),
                            dstSize = IntSize(TARGET_SIZE_PX.toInt(), TARGET_SIZE_PX.toInt())
                        )

                    }
                }

                //Jonas
                // jordklode
                drawImage(
                    image = earthImage,
                    topLeft = Offset(
                        x = gameViewModel.earthOffsetX.value,
                        y = size.height - earthHeight
                    )
                )
            }
            Box(modifier = Modifier
                .fillMaxSize()
                .then(Modifier.drawBehind { tick.value }) // Føen
            )
        }
    }
}
