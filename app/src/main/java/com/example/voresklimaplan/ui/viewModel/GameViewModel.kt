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

//Linea
class GameViewModel: ViewModel() {
    var game: Game by mutableStateOf(Game())
    var moveDirection: MoveDirection by mutableStateOf(MoveDirection.None)
    var screenWidth: Int by mutableIntStateOf(800)
    var screenHeight:  Int by mutableIntStateOf(1600) //why this state og hardcodet på samme tid
    val earthOffsetX: Animatable<Float, AnimationVector1D> by mutableStateOf(Animatable(0f))
    var earthHeight by mutableIntStateOf(0)
    var earthWidth by mutableIntStateOf(0)
    private val targetSize = 50 // i pixels, afhænger af dp til px konvertering
    var hasCenteredEarth by mutableStateOf(false) //??

    val activeGameTargets = mutableStateListOf<FallingGameTarget>() //den bruger de aktive
    var score by mutableIntStateOf(0)

    private val yPositions = mutableMapOf<Long, Float>()

    //Game targets
    //Føen
    val gameTargets = listOf(
        GameTarget("Bike", true, 2131165295),
        GameTarget("Windmill", true, 2131165302),
        GameTarget("Solar panel", true, 2131165300),
        GameTarget("Cow", false, 2131165334),
        GameTarget("Diesel", false, 2131165335),
        GameTarget("Apple", true, 2131165333),
        GameTarget("Plane", false, 2131165336)
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
                delay(5000L)
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
        println("game stopped")
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

            //hvis ikke element ikke fanges af jorden forsvinder det
            if (newY > screenHeight) {
                targetsToRemove.add(target)
            }

            //hvis et element fanges af jorden så:
            if (checkCollision(target, density)) {
                when (target.goodForClimate) {
                    //du rammer en god ting og får point
                    true -> {
                        println("juhu du har valgt en god ting")
                        score += 10
                        targetsToRemove.add(target)
                    }

                    //du rammer der ikke er god for klimaet  og game over
                    false -> {
                        println("you snooze you lose")
                        stopGame()

                        //sende de nye fangede point til firestore
                        //vises game over skærm
                        //
                    }
                }
            }
        }
        // Fjern fangede eller forsvundne targets
        targetsToRemove.forEach {
            activeGameTargets.remove(it)


            // yPositions.remove(it.id)
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

        val targetSizePx = 100 * density

        val targetRect = Rect (
            offset = Offset(target.xCordinate.toFloat(), target.yCordinate.toFloat()),
            size = Size(targetSizePx, targetSizePx)
        )
        println("Collision: ${earthRect.overlaps(targetRect)}")
        return earthRect.overlaps(targetRect)
    }
}