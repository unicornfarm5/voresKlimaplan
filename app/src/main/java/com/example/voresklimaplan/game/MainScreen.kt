package com.example.voresklimaplan.game

import android.graphics.Rect
import android.media.Image
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.voresklimaplan.R
import com.example.voresklimaplan.common.TextFontGaming
import com.example.voresklimaplan.game.domain.Game
import com.example.voresklimaplan.game.domain.GameStatus
import com.example.voresklimaplan.game.domain.MoveDirection
import com.example.voresklimaplan.game.util.detectMoveGesture
import com.example.voresklimaplan.ui.viewModel.ClassesViewModel
import com.example.voresklimaplan.ui.viewModel.GameViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.LineHeightStyle
import com.example.voresklimaplan.game.domain.gameTargets.FallingGameTarget

//Føen og Jonas
@Composable
fun MainScreen(
    navController: NavController,
    gameViewModel: GameViewModel = viewModel()) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val background = ImageBitmap.imageResource(R.drawable.background_game)
    val earthImage = ImageBitmap.imageResource(R.drawable.game_earth)
    val screenWidth = remember { mutableStateOf(0) }
    val screenHeight = remember { mutableStateOf(0) }

    // Load all target images into cache
    val imageCache = remember { mutableMapOf<Int, ImageBitmap>() }

    gameViewModel.gameTargets.forEach { target ->
        if (imageCache[target.imageId] == null) {
            imageCache[target.imageId] = ImageBitmap.imageResource(id = target.imageId)
        }
    }

    LaunchedEffect(Unit) {
        gameViewModel.startGame()
    }

    LaunchedEffect(gameViewModel.game.status) {
        if (gameViewModel.game.status == GameStatus.Started) {
            var lastFrameTime = 0L
            var spawnCooldown = 0L

            while (gameViewModel.game.status == GameStatus.Started) {
                withFrameMillis { frameTime ->
                    val delta = if (lastFrameTime == 0L) 0L else frameTime - lastFrameTime
                    lastFrameTime = frameTime

                    gameViewModel.updateTargetPosition(delta)

                    spawnCooldown += delta
                    if (spawnCooldown >= 2000L && gameViewModel.activeGameTargets.size < 10) {
                        gameViewModel.activeGameTargets.add(gameViewModel.createRandomTarget())
                        spawnCooldown = 0L
                    }

                    val earthTop = screenHeight.value - earthImage.height

// Tjek for kollision med jordkloden
                    val items = gameViewModel.activeGameTargets.toList() // Lav en sikker kopi

                    val toRemove = mutableListOf<FallingGameTarget>()

                    items.forEach { target ->
                        val targetBottom = target.yCordinate + 150
                        if (targetBottom >= earthTop) {
                            if (target.goodForClimate) {
                                gameViewModel.score += 10
                            }
                            toRemove.add(target)
                        }
                        // Fjern targets, der er kollideret med jorden
                        toRemove.forEach { gameViewModel.activeGameTargets.remove(it) }
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                screenWidth.value = it.size.width
                screenHeight.value = it.size.height
                gameViewModel.screenWidth = it.size.width
                gameViewModel.screenHeight = it.size.height
            }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    val newOffset = (gameViewModel.earthOffsetX.value + dragAmount)
                        .coerceIn(0f, screenWidth.value - earthImage.width.toFloat())
                    scope.launch {
                        gameViewModel.earthOffsetX.snapTo(newOffset)
                    }
                }
            }


    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Baggrund: brug native canvas for at sikre korrekt stræk
            drawIntoCanvas { canvas ->
                val paint = Paint()
                val src = Rect(0, 0, background.width, background.height)
                val dst = Rect(0, 0, size.width.toInt(), size.height.toInt())

                canvas.nativeCanvas.drawBitmap(
                    background.asAndroidBitmap(),
                    src,
                    dst,
                    paint.asFrameworkPaint()
                )
            }

            val targetSize = 200f

            // Draw targets
            gameViewModel.activeGameTargets.forEach { target ->
                val bitmap = imageCache[target.imageId] ?: return@forEach
                withTransform({
                    translate(left = target.xCordinate.toFloat(), top = target.yCordinate.toFloat())
                    scale(
                        scaleX = targetSize / bitmap.width,
                        scaleY = targetSize / bitmap.height
                    )
                }) {
                    drawImage(
                        image = bitmap,
                        topLeft = Offset.Zero
                    )
                }
            }


            // Draw Earth
            drawImage(
                image = earthImage,
                topLeft = Offset(
                    x = gameViewModel.earthOffsetX.value,
                    y = size.height - earthImage.height
                )
            )
        }

        Text(
            text = "Point: ${gameViewModel.score}",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
        )
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
        androidx.compose.animation.core.Animatable(0f) //
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