package com.example.voresklimaplan.ui.viewModel

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class ClassesViewModelTest {

    // todo: Til dette har vi fået hjælp af ChatGPT


    // Linea og Nikoleta
    @Test
    fun fetchAllClassrooms_successfully() = runBlocking {
        // Giver tilladelse fra Firebase
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val firestore = FirebaseFirestore.getInstance()

        // Hent alle dokumenter fra "Classroom"
        val snapshot = firestore.collection("Classroom").get().await()

        // Google Truth assertions
        assertThat(snapshot).isNotNull()
        assertThat(snapshot.isEmpty).isFalse()

        // Udskriver hver klasse
        for (doc in snapshot.documents) {
            println("Classroom: ${doc.id} => ${doc.data}")
            assertThat(doc.data).isNotEmpty()
        }
    }

    // Linea og Nikoleta
    @Test
    fun pushScoreToFirestore_successfully() = runBlocking {
        // Giver tilladelse fra Firebase
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val firestore = FirebaseFirestore.getInstance()

        // Giver testen et unikt ID
        val userId = UUID.randomUUID().toString()
        val testScore = 100

        // Upload til score i firebase
        val data = hashMapOf("score" to testScore)
        firestore.collection("scores").document(userId).set(data).await()

        // Hent den igen for at se at det virkede
        val snapshot = firestore.collection("scores").document(userId).get().await()

        // Google Truth checks
        assertThat(snapshot.exists()).isTrue()
        assertThat(snapshot.getLong("score")?.toInt()).isEqualTo(testScore)

        println("Score uploaded and verified: $testScore for user $userId")

    }
}
