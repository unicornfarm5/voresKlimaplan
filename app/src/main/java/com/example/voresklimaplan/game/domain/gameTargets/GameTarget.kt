package com.example.voresklimaplan.game.domain.gameTargets

//Linea
data class GameTarget(
    val targetName: String,
    val goodForClimate: Boolean,
    val imageId: Int //det er lokasionen til billedet - det finder man ved at println(R.drawable.game_bike) i main
)