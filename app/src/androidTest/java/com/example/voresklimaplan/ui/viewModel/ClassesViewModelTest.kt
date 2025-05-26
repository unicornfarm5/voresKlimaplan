package com.example.voresklimaplan.ui.viewModel

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ClassesViewModelTest {

//Linea og Nikoleta
    @Test
    fun fetchAllClassrooms_successfully() = runBlocking {
        //Giver tilladelse fra Firebase
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val firestore = FirebaseFirestore.getInstance()

        // Hent alle dokumenter fra "Classroom"-samlingen
        val snapshot = firestore.collection("Classroom").get().await()

        // Google Truth assertions
        assertThat(snapshot).isNotNull()
        assertThat(snapshot.isEmpty).isFalse()

        // Udskriv hver klasse
        for (doc in snapshot.documents) {
            println("Classroom: ${doc.id} => ${doc.data}")
            assertThat(doc.data).isNotEmpty()
        }
    }
}
