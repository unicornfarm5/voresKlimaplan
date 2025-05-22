package com.example.voresklimaplan.ui.viewModel

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voresklimaplan.game.domain.Game
import com.example.voresklimaplan.game.domain.GameStatus
import com.example.voresklimaplan.game.domain.MoveDirection
import com.example.voresklimaplan.game.domain.gameTargets.FallingGameTarget
import com.example.voresklimaplan.game.domain.gameTargets.GameTarget
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.Int
import kotlin.random.Random
import kotlin.ranges.random

class GameViewModel: ViewModel() {
    val game: Game by mutableStateOf(Game())
    var moveDirection: MoveDirection by mutableStateOf(MoveDirection.None)
    var screenWidth: Int by mutableIntStateOf(800)
    var screenHeight:  Int by mutableIntStateOf(1600)
    val earthOffsetX: Animatable<Float, AnimationVector1D> by mutableStateOf(Animatable(0f))

    val activeGameTargets = mutableStateListOf<FallingGameTarget>() //den bruger de aktive
    // Game targets
    val gameTargets = listOf(
        GameTarget("Cykel", true, 2131165295),
        GameTarget("Vindmølle", true, 2131165302),
        GameTarget("Solceller", true, 2131165300)
    )

    //bruges til at lave nye FallingGameTargets og bruges når vi tilføjer dem til ActivegameTargetListen
    fun createRandomTarget(): FallingGameTarget {
        val gameTarget = gameTargets[(0..gameTargets.size - 1).random()]
        return FallingGameTarget(
            targetName = gameTarget.targetName,
            goodForClimate = gameTarget.goodForClimate,
            imageId = gameTarget.imageId,
            id = System.currentTimeMillis(),
            xCordinate = (0..screenWidth).random(),
            yCordinate= 0
        )
    }

    //skal kaldes ved game start og skal derfor launches inde i gameScreen/mainScreen så spillet starter
    fun startGame(
        //context: Context, // giver adgang til at oprette viewet
    ) {
        //først opdateres gameStatus
        game.status = GameStatus.Started
        //så kører spawning nemlig
        viewModelScope.launch {
            while (game.status == GameStatus.Started) {
                activeGameTargets.add(createRandomTarget())
                delay(2000L)
            }
        }
    }
    fun stopGame() {
        game.status = GameStatus.Over
    }

}