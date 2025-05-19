package com.example.voresklimaplan.ui.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voresklimaplan.data.Classroom
import com.example.voresklimaplan.data.FirestoreRepository
import kotlinx.coroutines.launch

class ClassesViewModel: ViewModel() {
private val firestoreRepository = FirestoreRepository() //Her oprettes en instans af FirestoreRepository

private val _classList = mutableStateListOf<Classroom>() //Her oprettes en StateList som holder styr på opdateringer i Classroom
    val classList: List<Classroom> get() = _classList //Listen kan kun læses udefra og kun Viewmodel kan ændre det.

    fun getAllClasses() {
            viewModelScope.launch { //Her Startes en coroutine, så netværkskoden ikke blokere UI når der hentes data
                try {
                    val result = firestoreRepository.getClassroomList() //Her gemmes resultat fra firestore
                    _classList.clear() //Rydder listen så der ikke kommer dubletter
                    _classList.addAll(result) //Tilføjer de hentede classes og opdatere UI da det er StateList

                    println(result)

                } catch (e: Exception) {
                    println("Fejl: ${e.message}")
                }
            }
    }
}
