package com.example.mandatoryassignment.repository


import com.example.mandatoryassignment.model.Person
import retrofit2.Call

import retrofit2.http.*


interface PersonsService {
    @GET("Persons?user_id={userId}")
    fun getPersons(@Path("userId") userId: String): Call<List<Person>>

    @GET("Persons")
    fun getAllPersons(): Call<List<Person>>
}