package com.example.voresklimaplan.ui.viewModel

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.voresklimaplan.game.domain.Game
import com.example.voresklimaplan.game.domain.MoveDirection
import com.example.voresklimaplan.game.domain.gameTargets.GameTarget

class GameViewModel: ViewModel() {
    val game: Game by mutableStateOf(Game())
    var moveDirection: MoveDirection by mutableStateOf(MoveDirection.None)


    var screenWidth: Int by mutableIntStateOf(800)
    var screenHeight:  Int by mutableIntStateOf(1600)

    val earthOffsetX: Animatable<Float, AnimationVector1D> by mutableStateOf(Animatable(0f))

    // Game targets
    val gameTargets = listOf(
        GameTarget("Cykel", true, 2131165295),
        GameTarget("Vindmølle", true, 2131165302),
        GameTarget("Solceller", true, 2131165300)
    )
    fun getRandomTarget(): GameTarget {
        return gameTargets[(1..gameTargets.size).random()]
    }

    //spørg Ane om den her fun burde være en method i GameTarget?
    fun spawningTarget(
        context: Context, // giver adgang til at oprette viewet
        parent: ViewGroup //er en container der kan indeholde views - her imageView
    ) {
        val gameTarget = getRandomTarget() // får et nyt gameTarget hver gang den kører
        val imageView = ImageView(context).apply {
            setImageResource(gameTarget.imageId)
            layoutParams = FrameLayout.LayoutParams(200, 200).apply {
                topMargin = 0 // Spawner øverst på skærmen
                leftMargin = (0..screenWidth).random() // Tilfældigt horisontalt
                id = gameTarget.imageId
            }
        }
        parent.addView(imageView)
    }
}