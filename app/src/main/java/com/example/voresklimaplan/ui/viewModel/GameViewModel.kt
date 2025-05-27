package com.example.voresklimaplan.ui.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.withFrameNanos
import com.example.voresklimaplan.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive



//Linea
class GameViewModel: ViewModel() {
    var game: Game by mutableStateOf(Game())
    var moveDirection: MoveDirection by mutableStateOf(MoveDirection.None)
    var screenWidth: Int by mutableIntStateOf(800)
    var screenHeight:  Int by mutableIntStateOf(1800) //why this state og hardcodet på samme tid
    val earthOffsetX: Animatable<Float, AnimationVector1D> by mutableStateOf(Animatable(0f))
    var earthHeight by mutableIntStateOf(0)
    var earthWidth by mutableIntStateOf(0)
    var hasCenteredEarth by mutableStateOf(false) //??
    val targetSizePx = 200
    private var spawnJob: Job? = null

    val activeGameTargets = mutableStateListOf<FallingGameTarget>() //den bruger de aktive
    var score by mutableIntStateOf(0)

    //Føen
    val gameTargets = listOf(
        GameTarget("Bike", true, R.drawable.game_bike),
        GameTarget("Windmill", true, R.drawable.game_windmill),
        GameTarget("Solar panel", true, R.drawable.game_solarpanel),
        GameTarget("Cow", false, R.drawable.game_cow),
        GameTarget("Diesel", false, R.drawable.game_diesel_car),
        GameTarget("Apple", true, R.drawable.game_apple),
        GameTarget("Plane", false, R.drawable.game_plane)
    )

    //Bruges til at lave nye FallingGameTargets og bruges når vi tilføjer dem til ActivegameTargetListen
    fun createRandomTarget(): FallingGameTarget {
        val gameTarget = gameTargets.random()

        val newTarget = FallingGameTarget(
            targetName = gameTarget.targetName,
            goodForClimate = gameTarget.goodForClimate,
            imageId = gameTarget.imageId,
            id = System.currentTimeMillis(),
            xCordinate = Random.nextInt(0, screenWidth - targetSizePx),
            yCordinate = 0f
        )
        return newTarget

    }

    //Skal kaldes ved game start og skal derfor launches inde i gameScreen/mainScreen så spillet starter
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun startGame(density: Float) {
        game.settings = game.settings.copy(targetSpeed = 1000f)
        game = game.copy(status = GameStatus.Started) //Først opdateres gameStatus
        activeGameTargets.clear()
        score = 0
        game = game.copy(status = GameStatus.Started)
        spawnJob?.cancel()

        //Så kører spawning nemlig
        spawnJob=viewModelScope.launch {
            while (isActive && game.status == GameStatus.Started) {
                if (activeGameTargets.size < 5) {
                    activeGameTargets.add(createRandomTarget())
                    println("Spawned new target")
                }
                delay(1000L)
            }

        }}

        fun stopGame() {
            game = game.copy(status = GameStatus.Over)
                spawnJob?.cancel()

        }

        //Chatgpth er bruget til koden neden under.
        fun updateTargetPosition(density: Float, deltaMillis: Long) {
            println("updateTargetPosition: deltaMillis = $deltaMillis, speed = ${game.settings.targetSpeed}")

            val speed = game.settings.targetSpeed
            val targetsToRemove = mutableListOf<FallingGameTarget>()

            activeGameTargets.forEach { target ->
                val deltaY = speed * (deltaMillis / 2000f)
                target.yCordinate += deltaY


                if (target.yCordinate > screenHeight) {
                  targetsToRemove.add(target)
                }

                //  Check collision
                if (checkCollision(target, density)) {
                    if (target.goodForClimate) {
                        score += 10
                        targetsToRemove.add(target)
                    } else {
                        game = game.copy(status = GameStatus.Over)
                        targetsToRemove.add(target)
                    }
                }
            }
            targetsToRemove.forEach {
                activeGameTargets.remove(it)
            }
        }


    private fun checkCollision(
        target: FallingGameTarget,
        density: Float
    ): Boolean {
        val earthY = screenHeight - earthHeight //Klodens y-position
        val earthX = earthOffsetX.value

        val earthRect = Rect(
            offset = Offset(earthX, earthY.toFloat()),
            size = Size(earthWidth.toFloat(), earthHeight.toFloat())
        )

        val targetSizePx: Int = (200 * density).toInt()

        val targetRect = Rect(
            offset = Offset(target.xCordinate.toFloat(), target.yCordinate.toFloat()),
            size = Size(targetSizePx.toFloat(), targetSizePx.toFloat())  )

        val collision = earthRect.overlaps(targetRect)
        return collision
    }


}



