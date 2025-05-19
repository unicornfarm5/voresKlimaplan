package com.example.voresklimaplan.game.domain.gameObjects

data class Game (
    val status: GameStatus = Game.Idle,
    val score: Int = 0,

)

data class GameSettings(
    val nijaSpeed: Float = 15f,
    val targetSpeed: Float = 30f
)