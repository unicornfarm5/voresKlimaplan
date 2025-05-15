package com.example.voresklimaplan.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class FirestoreRepository {
    fun updateScoreboard (classroom: Classroom) {
        val classRoomCollection = Firebase.firestore.collection("Classroom") //Her defineres collection arbejdes med


    }
}


/*
//Her håndteres databasen (Slags mellemmand mellem database og app)

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