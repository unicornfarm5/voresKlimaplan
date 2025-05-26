package com.example.voresklimaplan.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

//Jonas
class FirestoreRepository {
    private val classRoomCollection = Firebase.firestore.collection("Classroom")
    //Her defineres hvilken collection der arbejdes med

    suspend fun getClassroomList():List<Classroom> {
        //Suspend betyder at denne funktion kan pauses og genoptages hvilket bruges i coroutines.
        return classRoomCollection
            .get()
            .await()
            .toObjects(Classroom::class.java) //Her konverteres de hentede dokumenter om til en instans af klassen Classroom
    }


    suspend fun updateScoreInFirebase(
        classroomId: String,
        newScore: Int
    ) {
        try {
            val classRoomDocument = classRoomCollection //Hent dokumentet med ID classroomId fra Classroom i Firestore
                .document(classroomId)
                .get()
                .await()

            val currentScore = classRoomDocument.getLong("score")?.toInt() ?: 0 //Her hentes nuværende score fra firestore
            //?.toInt(): Hvis getLong(...) ikke er null, så lav det om til en Int
            //Hvis alt før ?: er null, så brug 0 i stedet
            val updatedScore = currentScore + newScore //Her lægges den nye score til firestore score

            classRoomCollection
                .document(classroomId)
                .update("score", updatedScore)
                .await()

        } catch (e: Exception) {
            Log.e("Firestore", "Fejl ved opdatering af score", e)
            throw e
        }
    }
}
