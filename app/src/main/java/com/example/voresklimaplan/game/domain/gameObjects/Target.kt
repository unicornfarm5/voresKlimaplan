package com.example.voresklimaplan.game.domain.gameObjects

import android.graphics.drawable.Animatable

//ide fra https://www.youtube.com/watch?v=LXZw2RyV06s
interface Target {
    val x: Float
    val y: Float
    val targetImg: Int //vi skal printe billederne for at få deres id for at bruge dem
        //not sure om den skal være her...
}

data class