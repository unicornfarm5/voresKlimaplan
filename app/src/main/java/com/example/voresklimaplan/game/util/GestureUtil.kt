package com.example.voresklimaplan.game.util

import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import com.example.voresklimaplan.game.domain.GameStatus

//Føen
//fra https://www.youtube.com/watch?v=LXZw2RyV06s&t=849s
//med github https://github.com/stevdza-san/NinjaBubble_Game
suspend fun AwaitPointerEventScope.detectMoveGesture(
    gameStatus: GameStatus,
    onLeft:() -> Unit,
    onRight:()-> Unit,
    onFingerLifted:() -> Unit,
    ){
    while (gameStatus == GameStatus.Started){
        val downEvent = awaitPointerEvent()
        val initialDown = downEvent.changes.firstOrNull { it.pressed}
        if(initialDown == null) continue

        val primaryPointerId = initialDown.id
        var previousPosition = initialDown.position

        while(true){
            val event = awaitPointerEvent()
            val change = event.changes.firstOrNull{
                it.id == primaryPointerId
            }

            if(change == null ||!change.pressed){
                onFingerLifted()
                break
            }

            val currentPosition = change.position
            val deltaX = currentPosition.x - previousPosition.x

            if (deltaX < 0 ){
                onLeft()
            } else if(deltaX > 0 ){
                onRight()
            }

            previousPosition = currentPosition
            change.consume()
        }
    }


}