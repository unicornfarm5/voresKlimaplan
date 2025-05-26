package com.example.voresklimaplan.ui.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voresklimaplan.data.Classroom
import com.example.voresklimaplan.data.FirestoreRepository
import kotlinx.coroutines.launch

//Jonas
class ClassesViewModel : ViewModel() {
    private val firestoreRepository = FirestoreRepository() // Her oprettes en instans af FirestoreRepository

    private val _classList = mutableStateListOf<Classroom>() // Her oprettes en StateList som holder styr på opdateringer i Classroom
    val classList: List<Classroom> get() = _classList // Listen kan kun læses udefra og kun Viewmodel kan ændre det.

    init {
        // Init gør at funktionen getAllClasses kører når man laver en instans af ViewModelen
        getAllClasses()
    }

    fun getAllClasses() {
        viewModelScope.launch { // Her startes en coroutine, så netværkskoden ikke blokerer UI når der hentes data
            try {
                val result = firestoreRepository.getClassroomList() // Her gemmes resultat fra Firestore
                _classList.clear() // Rydder listen så der ikke kommer dubletter
                _classList.addAll(result) // Tilføjer de hentede classes og opdaterer UI da det er StateList

                println(result)

            } catch (e: Exception) {
                println("Fejl: ${e.message}")
            }
        }
    }

    //Linea
    fun saveScoreInFireBase(classroomId: String, score: Int) {
        viewModelScope.launch {
            try {
                firestoreRepository.updateScoreInFirebase(classroomId, score)
            } catch (e: Exception) {
                println("Fejl: ${e.message}")
            }
        }
    }
}
