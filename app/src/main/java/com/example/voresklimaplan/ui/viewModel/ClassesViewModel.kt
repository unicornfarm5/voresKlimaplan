package com.example.voresklimaplan.ui.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voresklimaplan.data.Classroom
import com.example.voresklimaplan.data.FirestoreRepository
import kotlinx.coroutines.launch

class ClassesViewModel: ViewModel() {
private val firestoreRepository = FirestoreRepository() //Her oprettes en instans af FirestoreRepository
private val classesList = mutableStateListOf<Classroom>()

    fun getAllClasses() {
        viewModelScope.launch { //Her Startes en coroutine, så netværkskoden ikke blokere UI når der hentes data
            val result = firestoreRepository.getClassroom()
            println(result)
        }
    }



}


/*
class FamilyViewModel: ViewModel() {
    private val familyRepository = FamilyRepository() //Private fordi det kun skal brugs i viewModel

    var familieNavn by mutableStateOf("")
    var email by mutableStateOf("")


    fun upload () { //Opretter et FamilieMedlem-objekt og beder famlilyRepository om at upload det til databasen
        familyRepository.addFamilyMember(
            FamilieMedlem(
                familieNavn = familieNavn,
                medlemmer = listOf(email),
            )
        )
    }
}
 */