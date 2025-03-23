package com.example.mandatoryassignment.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.mandatoryassignment.model.Person
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PersonsRepository {

    private val baseUrl = "https://birthdaysrest.azurewebsites.net/api/"

    private val personsService : PersonsService
    val persons: MutableState<List<Person>> = mutableStateOf(listOf())
    val errorMessage = mutableStateOf("")


    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        personsService = build.create(PersonsService::class.java)

        //getPersons("anbo@zealand.dk")
    }




    fun getPersons(userId: String? = null) {
        Log.d("PersonsRepository", "Fetching persons for user: $userId")
        personsService.getPersons(userId).enqueue(object : Callback<List<Person>>{
            override fun onResponse(call: Call<List<Person>>, response: Response<List<Person>>){
                if (response.isSuccessful){
                    Log.d("PersonsRepository", "Response successful: ${call.request().url()}")
                    val personList: List<Person>? = response.body()
                    persons.value = personList ?: emptyList()
                    errorMessage.value = ""
                }
                else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                }
            }

            override fun onFailure(call: Call<List<Person>>, t: Throwable){
                val message = t.message ?: "No connection to back-end"
                errorMessage.value = message
            }
        })
    }



    //    fun getPersons(userId: String) {
//        personsService.getPersons(userId).enqueue(object : Callback<List<Person>>{
//            override fun onResponse(call: Call<List<Person>>, response: Response<List<Person>>){
//                if (response.isSuccessful){
//                    val personList: List<Person>? = response.body()
//                    persons.value = personList ?: emptyList()
//                    errorMessage.value = ""
//                }
//                else {
//                    val message = response.code().toString() + " " + response.message()
//                    errorMessage.value = message
//                }
//            }
//
//            override fun onFailure(call: Call<List<Person>>, t: Throwable){
//                val message = t.message ?: "No connection to back-end"
//                errorMessage.value = message
//            }
//        })
//    }



}