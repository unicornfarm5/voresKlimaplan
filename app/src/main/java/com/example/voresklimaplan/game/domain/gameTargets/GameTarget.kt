package com.example.voresklimaplan.game.domain.gameTargets


//Linea
open class GameTarget(
    val targetName: String,
    var goodForClimate: Boolean,
    val imageId: Int //R.drawable.filnavn
)

class FallingGameTarget(
    targetName: String,
    goodForClimate: Boolean,
    imageId: Int,
    val id: Long, // Long fordi vi bruger timestamp til id og den er en Long //System.currentTimeMillis()

    //kordinater
    var xCordinate: Int,
    var yCordinate: Float //Føen
): GameTarget(targetName, goodForClimate, imageId,)
