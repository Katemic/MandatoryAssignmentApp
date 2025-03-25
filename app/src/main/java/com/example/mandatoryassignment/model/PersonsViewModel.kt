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


    fun createPerson(person: Person) {
        repository.createPerson(person)
    }

    fun deletePerson(id: Int) {
        repository.delete(id)
    }

    fun getPersonById(id: Int, onResult: (Person?) -> Unit) {
        return repository.getById(id, onResult)
    }

    fun updatePerson(id: Int, person: Person) {
        repository.updatePerson(id, person)
    }

    fun sortByName(ascending: Boolean) {
        repository.sortByName(ascending)
    }

}