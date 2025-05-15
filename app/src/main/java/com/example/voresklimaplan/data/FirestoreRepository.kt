package com.example.voresklimaplan.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class FirestoreRepository {
    private val classRoomCollection = Firebase.firestore.collection("Classroom") //Her defineres hvilken collection der arbejdes med

    fun updateScoreboard (classroom: Classroom) {
        classRoomCollection
    }

    suspend fun getClassroom():List<Classroom> { //Suspend betyder at denne funktion kan pauses og genoptages hvilket bruges i coroutines.
        return classRoomCollection
            .get()
            .await()
            .toObjects(Classroom::class.java) //Her konverteres de hentede dokumenter om til en instans af klassen Classroom
    }
}



/*
class FamilyRepository {
    fun addFamilyMember(familieMedlem: FamilieMedlem) {
        try {
            val familyCollection = Firebase.firestore.collection("familier") //Her defineres collection arbejdes med

            familyCollection //Her tilføjes data
                .add(familieMedlem) //Tilføjer nyt dokument til collection (Indeholder ikke data endnu)
                .addOnSuccessListener { documentReference ->
                    println("Tilføjet med ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    println("Error: $e")
                }
        } catch (e: Exception) {
            println("Error: $e")
        }
    }
}
 */