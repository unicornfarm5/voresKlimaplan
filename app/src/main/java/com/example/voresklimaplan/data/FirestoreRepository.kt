package com.example.voresklimaplan.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class FirestoreRepository {
    private val classRoomCollection = Firebase.firestore.collection("Classroom") //Her defineres collection arbejdes med
    fun updateScoreboard (classroom: Classroom) {
        classRoomCollection
    }

    suspend fun getClassroom():List<Classroom> {
        return classRoomCollection
            .get()
            .await()
            .toObjects(Classroom::class.java) //??
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