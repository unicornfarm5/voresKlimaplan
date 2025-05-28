package com.example.voresklimaplan.ui.viewModel

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voresklimaplan.game.domain.Game
import com.example.voresklimaplan.game.domain.GameStatus
import com.example.voresklimaplan.game.domain.MoveDirection
import com.example.voresklimaplan.game.domain.gameTargets.FallingGameTarget
import com.example.voresklimaplan.game.domain.gameTargets.GameTarget
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import androidx.compose.ui.graphics.ImageBitmap
import com.example.voresklimaplan.R
import com.example.voresklimaplan.data.FirestoreRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive

//Linea
class GameViewModel: ViewModel() {
    var game: Game by mutableStateOf(Game())
    var moveDirection: MoveDirection by mutableStateOf(MoveDirection.None)
    var screenWidth: Int by mutableIntStateOf(800)
    var screenHeight:  Int by mutableIntStateOf(1800)
    val earthOffsetX: Animatable<Float, AnimationVector1D> by mutableStateOf(Animatable(0f))
    var earthHeight by mutableIntStateOf(0)
    var earthWidth by mutableIntStateOf(0)
    var layoutReady  by mutableStateOf(false)
    val imageCache: MutableMap<Int, ImageBitmap> = mutableMapOf()
    var hasCenteredEarth by mutableStateOf(false)
    var targetSizePx: Float = 0f
    private var spawnJob: Job? = null

    val activeGameTargets = mutableStateListOf<FallingGameTarget>()
    var score by mutableIntStateOf(0)

    //Føen og Linea
    val gameTargets = listOf(
        GameTarget("Bike", true, R.drawable.game_bike),
        GameTarget("Windmill", true, R.drawable.game_windmill),
        GameTarget("Solar panel", true, R.drawable.game_solarpanel),
        GameTarget("Cow", false, R.drawable.game_cow),
        GameTarget("Diesel", false, R.drawable.game_diesel_car),
        GameTarget("Apple", true, R.drawable.game_apple),
        GameTarget("Plane", false, R.drawable.game_plane)
    )


    //Linea og Føen
    //Bruges til at lave nye FallingGameTargets og bruges når vi tilføjer dem til ActivegameTargetListen
    fun createRandomTarget(): FallingGameTarget {
        val gameTarget = gameTargets.random()
        val newTarget = FallingGameTarget(
            targetName = gameTarget.targetName,
            goodForClimate = gameTarget.goodForClimate,
            imageId = gameTarget.imageId,
            id = System.currentTimeMillis(),
            xCordinate = Random.nextInt(0, screenWidth - targetSizePx.toInt()),
            yCordinate = 0f
        )
        return newTarget
    }

    //Føen
    //Skal kaldes ved game start og skal derfor launches inde i gameScreen/mainScreen så spillet starter
    fun startGame(density: Float) {
        game.settings = game.settings.copy(targetSpeed = 1000f)
        game = game.copy(status = GameStatus.Started) //Først opdateres gameStatus
        activeGameTargets.clear()
        score = 0
        spawnJob?.cancel() // todo: chatten her

        //Linea og Føen
        //Så kører spawning nemlig
        spawnJob = viewModelScope.launch {
            while (isActive && game.status == GameStatus.Started) {
                if (activeGameTargets.size < 5) { // gør der ikke er så mange targets i frame
                    activeGameTargets.add(createRandomTarget())
                    println("Spawned new target")
                }
                delay(2000L)
            }
        }
    }

    //Føen
        fun stopGame(
            onGameOver: () -> Unit
        ) {
            game = game.copy(status = GameStatus.Over)
            spawnJob?.cancel()
            onGameOver()
        }


        // todo: Chatgpth er bruget til koden neden under.
        fun updateTargetPosition(
            deltaMillis: Long,
            onGameOver: () -> Unit
        ) {
            val speed = game.settings.targetSpeed
            val targetsToRemove = mutableListOf<FallingGameTarget>()

            activeGameTargets.forEach { target ->
                val deltaY = speed * (deltaMillis / 2000f)
                target.yCordinate += deltaY


                if (target.yCordinate > screenHeight) {
                  targetsToRemove.add(target)
                }

                //  Linea og Føen
                if (checkCollision(target)) {
                    if (target.goodForClimate) {
                        score += 10
                        targetsToRemove.add(target)
                    } else {
                        stopGame(onGameOver)
                        targetsToRemove.add(target)
                    }
                }
            }
            targetsToRemove.forEach {
                activeGameTargets.remove(it)
            }
    }


    //Jonas
    // todo: chatten brugt under
    private fun checkCollision(
        target: FallingGameTarget
    ): Boolean {
        val earthY = screenHeight - earthHeight //Klodens y-position
        val earthX = earthOffsetX.value

        val earthRect = Rect(
            offset = Offset(earthX, earthY.toFloat()),
            size = Size(earthWidth.toFloat(), earthHeight.toFloat())
        )

        val targetSizePx = targetSizePx

        val targetRect = Rect(
            offset = Offset(target.xCordinate.toFloat(), target.yCordinate),
            size = Size(targetSizePx, targetSizePx)  )

        val collision = earthRect.overlaps(targetRect)
        return collision
    }

}



