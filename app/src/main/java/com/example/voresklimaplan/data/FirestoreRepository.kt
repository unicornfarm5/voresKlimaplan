package com.example.voresklimaplan.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

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
        newScore: Int) {
        try {
            classRoomCollection
                .document(classroomId)
                .update("score", newScore)
                .await()
        } catch (e: Exception) {
            Log.e("Firestore", "Fejl ved opdatering af score", e)
            throw e
        }
    }
}

