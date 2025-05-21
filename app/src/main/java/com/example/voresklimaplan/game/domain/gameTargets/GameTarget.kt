package com.example.voresklimaplan.game.domain.gameTargets

class GameTarget(
    val targetName: String,
    val goodForClimate:Boolean,
    val image: Int //det er lokasionen til billedet - det finder man ved at println(R.drawable.game_bike) i main
) {
}


val targets = listOf(
    GameTarget("Cykel", true, 2131165295),
    GameTarget("Vindmølle", true, 2131165302),
    GameTarget("Solceller", true, 2131165300)
)