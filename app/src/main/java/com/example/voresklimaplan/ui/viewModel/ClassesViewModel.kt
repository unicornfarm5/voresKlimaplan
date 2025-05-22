package com.example.voresklimaplan.ui.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voresklimaplan.data.Classroom
import com.example.voresklimaplan.data.FirestoreRepository
import kotlinx.coroutines.launch

class ClassesViewModel : ViewModel() {
    private val firestoreRepository = FirestoreRepository() // Her oprettes en instans af FirestoreRepository

    private val _classList = mutableStateListOf<Classroom>() // Her oprettes en StateList som holder styr på opdateringer i Classroom
    val classList: List<Classroom> get() = _classList // Listen kan kun læses udefra og kun Viewmodel kan ændre det.

    init {
        // Init gør at funktionen getAllClasses kører når man laver en instans af ViewModelen
        getAllClasses()
    }

    // ved afsluttet spil skal Firestore's score opdateres + den funktion skal køre igen så scoreboard + by er up to date

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
}
