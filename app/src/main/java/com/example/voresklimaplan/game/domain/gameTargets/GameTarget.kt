package com.example.voresklimaplan.game.domain.gameTargets

import com.example.voresklimaplan.game.domain.Game

//Linea
open class GameTarget(
    val targetName: String,
    var goodForClimate: Boolean,
    val imageId: Int //det er lokasionen til billedet                                                 - det finder man ved at println(R.drawable.game_bike) i main
)

class FallingGameTarget(
    targetName: String,
    goodForClimate: Boolean,
    imageId: Int, //så kan vi holde styr på dem selv når der er flere elbiler på skærmen
    val id: Long, //fordi vi bruger timestamp til id og den er en Long //System.currentTimeMillis()

    //kordinater
    var xCordinate: Int,
    var yCordinate: Int
): GameTarget(targetName, goodForClimate, imageId,)
