package com.example.mandatoryassignment.model

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.mandatoryassignment.repository.PersonsRepository


class PersonsViewModel : ViewModel() {

    private val repository = PersonsRepository()

    val persons: State<List<Person>> = repository.persons
    val errorMessage: State<String> = repository.errorMessage


    fun getPersons(userId: String) {
        repository.getPersons(userId)
    }


}