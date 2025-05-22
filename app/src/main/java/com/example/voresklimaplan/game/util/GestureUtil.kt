package com.example.voresklimaplan.game.util

import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import com.example.voresklimaplan.game.domain.GameStatus

//Føen: funktionen detectMoveGesture(...) er en costum gesture detector som kan bruges i spil,
// hvor man skal trække fingeren mod venstre eller højre


suspend fun AwaitPointerEventScope.detectMoveGesture( //suspend bruges i pointerInput-blokke fordi vi venter på touch-events. AwaitPointerEventScope giver adgang til fingerinput(tryk, bevægelser osv.)
    gameStatus: GameStatus, //Gamestatus checker om spillet kører
    onLeft:() -> Unit,
    onRight:()-> Unit, // funktioner som vi selv sender ind, som bliver kaldt når noget sker
    onFingerLifted:() -> Unit,
    ){
    while (gameStatus == GameStatus.Started){ // gesture funktion kører kun hvis spillet er igang

        val downEvent = awaitPointerEvent() // venter på "Pointer event" - altså når brugeren trykker
        val initialDown = downEvent.changes.firstOrNull { it.pressed} // finder første sted hvor der trykkes på skærmen
        if(initialDown == null) continue // hvis ingen trykker starter den forfra

        // gemmer id på hvilken finger det er og hvor den først rørte skærmen
        val primaryPointerId = initialDown.id
        var previousPosition = initialDown.position

        // uendelig løkke som konstant venter på fingerbevægelse
        while(true){
            val event = awaitPointerEvent()
            val change = event.changes.firstOrNull{ // finger den aktuelle position for samme finger
                it.id == primaryPointerId
            }
        //Hvis fingeren løfter/ man ikke kan finde fingeren --> betyder det at fingeren er løftet
            if(change == null ||!change.pressed){
                onFingerLifted()
                break // Vi kalder onFingerLifted og stopper løkken
            }
        // finder ud af om brugeren swipede venstre eller højre
            val currentPosition = change.position
            val deltaX = currentPosition.x - previousPosition.x // hvor fingeren har bevæget sig vandret

            if (deltaX < 0 ){ // hvis negativt bevæg til venstre
                onLeft()
            } else if(deltaX > 0 ){ // hvis positiv bevæg til højre
                onRight()
            }
            // gem den nye position og brug eventet
            previousPosition = currentPosition
            change.consume() // consume gør at der ikke er andre som reagere på samme event
        }
    }
}