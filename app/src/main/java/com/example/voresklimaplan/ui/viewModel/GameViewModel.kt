package com.example.voresklimaplan.ui.viewModel

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

//Linea
class GameViewModel: ViewModel() {
    var game: Game by mutableStateOf(Game())
    var moveDirection: MoveDirection by mutableStateOf(MoveDirection.None)
    var screenWidth: Int by mutableIntStateOf(800)
    var screenHeight:  Int by mutableIntStateOf(1600)
    val earthOffsetX: Animatable<Float, AnimationVector1D> by mutableStateOf(Animatable(0f))
    //private var gameLoopJob: Job? = null //noget fra chatten som nok ikke skal bruges mere

    val activeGameTargets = mutableStateListOf<FallingGameTarget>() //den bruger de aktive

    private val yPositions = mutableMapOf<Long, Float>()

    //Game targets
    val gameTargets = listOf(
        GameTarget("Cykel", true, 2131165295),
        GameTarget("Vindmølle", true, 2131165302),
        GameTarget("Solceller", true, 2131165300)
    )

    //bruges til at lave nye FallingGameTargets og bruges når vi tilføjer dem til ActivegameTargetListen
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
    //skal kaldes ved game start og skal derfor launches inde i gameScreen/mainScreen så spillet starter

    fun startGame() {
        //context: Context, // giver adgang til at oprette viewet
        game.settings = game.settings.copy(targetSpeed = 60f)
        //først opdateres gameStatus
        game.status = GameStatus.Started
        activeGameTargets.clear()
        yPositions.clear()

        //så kører spawning nemlig
        viewModelScope.launch {
            // Spawn a new target every 2 seconds
            while (game.status == GameStatus.Started) {
                activeGameTargets.add(createRandomTarget())
                delay(2000L)
            }
        }

        viewModelScope.launch {
            while (game.status == GameStatus.Started) {
                updateTargetPosition()
                delay(16L)
            }
        }
    }

    fun stopGame() {
        game.status = GameStatus.Over
        //gameLoopJob?.cancel()
        //gameLoopJob = null
        activeGameTargets.clear()
        yPositions.clear()
    }

    //Chatgpth er bruget til koden neden under.
    fun updateTargetPosition() {
        val speed = (game.settings.targetSpeed * 0.05f) //Denne justere hastigheden baseret på faktoren i Game.kt

        val toResetTargets = mutableListOf<FallingGameTarget>() // En midlertidig liste til targets, der skal nulstilles

        activeGameTargets.forEach { target ->
            val currentY = yPositions[target.id] ?: 0f //Henter den nuværende y-position
            val newY = currentY + speed //Denne udregner den nyge y-position baseret på farten.
            yPositions[target.id] = newY //Her bliver y-positionen opdateret i map

            target.yCordinate = newY.toInt()

            //Hvis target er under skærmkanten bliver den resetet.
            if (newY > screenHeight) {
                toResetTargets.add(target)
            }
        }

        //Her bliver targetsene resetet, når de falder ned under jorden.
        toResetTargets.forEach { target ->
            yPositions[target.id] = 0f
            target.xCordinate = Random.nextInt(0, screenWidth)
            target.yCordinate = 0
        }
    }
}
