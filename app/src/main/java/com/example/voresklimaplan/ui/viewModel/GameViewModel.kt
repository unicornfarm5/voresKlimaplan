package com.example.voresklimaplan.ui.viewModel

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.positionChange
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voresklimaplan.game.domain.Game
import com.example.voresklimaplan.game.domain.GameStatus
import com.example.voresklimaplan.game.domain.MoveDirection
import com.example.voresklimaplan.game.domain.gameTargets.FallingGameTarget
import com.example.voresklimaplan.game.domain.gameTargets.GameTarget
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import com.google.firebase.firestore.FirebaseFirestore

        class GameViewModel: ViewModel() {
            val db = FirebaseFirestore.getInstance()
            var game: Game by mutableStateOf(Game())
            var moveDirection: MoveDirection by mutableStateOf(MoveDirection.None)
            var screenWidth: Int by mutableIntStateOf(800)
            var screenHeight:  Int by mutableIntStateOf(1600)
            val earthOffsetX: Animatable<Float, AnimationVector1D> by mutableStateOf(Animatable(0f))
            var score by mutableIntStateOf(0)

            //private var gameLoopJob: Job? = null //noget fra chatten som nok ikke skal bruges mere
            val activeGameTargets = mutableStateListOf<FallingGameTarget>() //den bruger de aktive



            private val yPositions = mutableMapOf<Long, Float>()

            //Game targets
            val gameTargets = listOf(
                GameTarget("Bike", true, 2131165295),
                GameTarget("Windmill", true, 2131165302),
                GameTarget("Solar panel", true, 2131165300),
                GameTarget("Cow", false, 2131165334),
                GameTarget("Diesel", false, 2131165335),
                GameTarget("Apple", true, 2131165333),
                GameTarget("Plane", false, 2131165336)


            )

            //bruges til at lave nye FallingGameTargets og bruges når vi tilføjer dem til ActivegameTargetListen
            fun createRandomTarget(): FallingGameTarget {
                val gameTarget = gameTargets.random()
                val newTarget = FallingGameTarget(
                    targetName = gameTarget.targetName,
                    goodForClimate = gameTarget.goodForClimate,
                    imageId = gameTarget.imageId,
                    id = System.currentTimeMillis(),
                    xCordinate = Random.nextInt(0, screenWidth),
                    yCordinate = 0
                )
                yPositions[newTarget.id] = 0f
                return newTarget
            }
            //skal kaldes ved game start og skal derfor launches inde i gameScreen/mainScreen så spillet starter

            fun startGame() {
                game.settings = game.settings.copy(targetSpeed = 170f)
                game.status = GameStatus.Started
                activeGameTargets.clear()
                yPositions.clear()

                }


            fun stopGame() {
                game.status = GameStatus.Over
                saveScoreToFirebase(score) // <-- gemmer score i Firebase
                activeGameTargets.clear()
                yPositions.clear()
            }
            fun saveScoreToFirebase(score: Int) {
                val scoreData = hashMapOf(
                    "score" to score,
                    "timestamp" to System.currentTimeMillis()
                )

                db.collection("scores")
                    .add(scoreData)
                    .addOnSuccessListener {
                        println("✅ Score gemt i Firebase")
                    }
                    .addOnFailureListener { e ->
                        println("❌ Fejl ved gemning: ${e.message}")
                    }
            }

            fun updateGame(deltaMillis: Long) {
                // logic here
            }

            fun updateTargetPosition(deltaMillis: Long) {
                val speed = game.settings.targetSpeed
                val toRemove = mutableListOf<FallingGameTarget>()

                activeGameTargets.forEach { target ->
                    val currentY = yPositions[target.id] ?: 0f
                    val deltaY = speed * (deltaMillis /1000f)
                    val newY = currentY + deltaY
                    yPositions[target.id] = newY
                    target.yCordinate = newY.toInt()

                    if (newY > screenHeight) toRemove.add(target)
                }

                toRemove.forEach { target ->
                    activeGameTargets.remove(target)
                    yPositions.remove(target.id)
                }
            }

            // Add detectHorizontalDragGestures manually if needed:
            suspend fun PointerInputScope.detectHorizontalDragGestures(
                onDrag: (PointerInputChange, Float) -> Unit
            ) {
                forEachGesture {
                    awaitPointerEventScope {
                        val down = awaitFirstDown()
                        var change = down
                        do {
                            val event = awaitPointerEvent()
                            val dragAmount = event.changes.first().positionChange().x
                            onDrag(change, dragAmount)
                            change = event.changes.first()
                        } while (event.changes.any { it.pressed })
                    }
                }
            }}

