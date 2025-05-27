package com.example.voresklimaplan.game.domain

data class Game(
    var status: GameStatus = GameStatus.NotStarted,
    var score: Int = 0,
    var settings: GameSettings = GameSettings()
)

data class GameSettings(
    val earthSpeed: Float = 15f,
    val targetSpeed: Float = 20f
)