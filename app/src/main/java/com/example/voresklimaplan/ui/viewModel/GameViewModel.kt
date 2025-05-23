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

class GameViewModel: ViewModel() {
    var game: Game by mutableStateOf(Game())
    var moveDirection: MoveDirection by mutableStateOf(MoveDirection.None)
    var screenWidth: Int by mutableIntStateOf(800)
    var screenHeight:  Int by mutableIntStateOf(1600)
    val earthOffsetX: Animatable<Float, AnimationVector1D> by mutableStateOf(Animatable(0f))
    var earthHeight by mutableIntStateOf(0)
    var earthWidth by mutableIntStateOf(0)
    private val targetSize = 50 // i pixels, afhænger af dp til px konvertering
    var hasCenteredEarth by mutableStateOf(false) //??

    val activeGameTargets = mutableStateListOf<FallingGameTarget>() //den bruger de aktive
    var score by mutableIntStateOf(0)

    private val yPositions = mutableMapOf<Long, Float>()

    //Game targets
    val gameTargets = listOf(
        GameTarget("Cykel", true, 2131165295),
        GameTarget("Vindmølle", true, 2131165302),
        GameTarget("Solceller", true, 2131165300)
    )

    //Bruges til at lave nye FallingGameTargets og bruges når vi tilføjer dem til ActivegameTargetListen
    fun createRandomTarget(): FallingGameTarget {
        val gameTarget = gameTargets.random()
        val newTarget = FallingGameTarget(
            targetName = gameTarget.targetName,
            goodForClimate = gameTarget.goodForClimate,
            imageId = gameTarget.imageId,
            id = System.currentTimeMillis(),
            xCordinate = Random.nextInt(0, screenWidth),
            yCordinate = 0
        )
        yPositions[newTarget.id] = 0f
        return newTarget
    }
    //Skal kaldes ved game start og skal derfor launches inde i gameScreen/mainScreen så spillet starter

    fun startGame(density: Float) {
        //context: Context, // giver adgang til at oprette viewet
        game.settings = game.settings.copy(targetSpeed = 60f)
        game.status = GameStatus.Started //Først opdateres gameStatus
        activeGameTargets.clear()
        yPositions.clear()

        //Så kører spawning nemlig
        viewModelScope.launch {
            // Spawn a new target every 2 seconds
            while (game.status == GameStatus.Started) {
                activeGameTargets.add(createRandomTarget())
                delay(2000L)
            }
        }

        viewModelScope.launch {
            while (game.status == GameStatus.Started) {
                updateTargetPosition(density)
                delay(16L)
            }
        }
    }

    fun stopGame() {
        game.status = GameStatus.Over
        activeGameTargets.clear()
        yPositions.clear()
    }

    //Chatgpth er bruget til koden neden under.
    fun updateTargetPosition(density: Float) {
        val speed = (game.settings.targetSpeed * 0.05f) //Denne justere hastigheden baseret på faktoren i Game.kt

        val targetsToRemove = mutableListOf<FallingGameTarget>()
        //val toResetTargets = mutableListOf<FallingGameTarget>() // En midlertidig liste til targets, der skal nulstilles

        activeGameTargets.forEach { target ->
            val currentY = yPositions[target.id] ?: 0f //Henter den nuværende y-position
            val newY = currentY + speed //Denne udregner den nyge y-position baseret på farten.
            yPositions[target.id] = newY //Her bliver y-positionen opdateret i map
            target.yCordinate = newY.toInt()

            if (checkColliosion(target, density)) {
                score += 1
                targetsToRemove.add(target)
            } else if (newY > screenHeight) {
                targetsToRemove.add(target)
            }
        }

        // Fjern fangede eller forsvundne targets
        targetsToRemove.forEach {
            activeGameTargets.remove(it)
            yPositions.remove(it.id)
        }
    }

    private fun checkColliosion(target: FallingGameTarget, density: Float): Boolean {
        val earthY = screenHeight - earthHeight //Klodens y-position
        val earthX = earthOffsetX.value

        val earthRect = Rect(
            offset = Offset(earthX, earthY.toFloat()),
            size = Size(earthWidth.toFloat(), earthHeight.toFloat())
        )

        val targetSizePx = 150 * density

        val targetRect = Rect (
            offset = Offset(target.xCordinate.toFloat(), target.yCordinate.toFloat()),
            size = Size(targetSizePx, targetSizePx)
        )
        return earthRect.overlaps(targetRect)

    }
}


