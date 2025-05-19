package com.example.voresklimaplan.ui.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voresklimaplan.data.Classroom
import com.example.voresklimaplan.data.FirestoreRepository
import kotlinx.coroutines.launch

class ClassesViewModel: ViewModel() {
private val firestoreRepository = FirestoreRepository() //Her oprettes en instans af FirestoreRepository

private val _classList = mutableStateListOf<Classroom>() //??
    val classList: List<Classroom> get() = _classList //??

    fun getAllClasses() {
            viewModelScope.launch { //Her Startes en coroutine, så netværkskoden ikke blokere UI når der hentes data
                try {
                    val result = firestoreRepository.getClassroomList() //Her gemmes resultat fra firestore
                    _classList.clear() //??
                    _classList.addAll(result)

                    println(result)

                } catch (e: Exception) {
                    println("Fejl: ${e.message}")
                }
            }
    }
}
