package com.example.voresklimaplan.game.domain.gameTargets

import com.example.voresklimaplan.game.domain.Game

//Linea
open class GameTarget(
    val targetName: String,
    val goodForClimate: Boolean,
    val imageId: Int //det er lokasionen til billedet - det finder man ved at println(R.drawable.game_bike) i main
    //vi opdagede at vi ikke kunne give dem alle et tilfældigt x og y fordi ellers
)

class FallingGameTarget(
    targetName: String,
    goodForClimate: Boolean,
    imageId: Int,
    //id findes for dem der falder så vi kan holde styr på dem
    //så kan vi holde styr på dem selv når der er flere elbiler på skæremen
    val id: Long, //fordi vi gerne bruge en timestamp og den er sørme en Lond
    //System.currentTimeMillis()

    //kordinater
    val xCordinate: Int,
    val yCordinate: Int
): GameTarget(targetName, goodForClimate, imageId,)
