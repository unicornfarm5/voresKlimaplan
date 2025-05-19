package com.example.voresklimaplan.game.domain

data class Game(
    val status: GameStatus = GameStatus.Idle,
    val score: Int = 0,
    val settings: GameSettings = GameSettings()
)

data class GameSettings(
    val earthSpeed: Float = 15f,
    val targetSpeed: Float=20f
)