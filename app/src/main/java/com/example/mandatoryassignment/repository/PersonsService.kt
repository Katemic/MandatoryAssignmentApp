package com.example.mandatoryassignment.repository


import com.example.mandatoryassignment.model.Person
import retrofit2.Call

import retrofit2.http.*


interface PersonsService {
//    @GET("Persons?user_id={userId}")
//    fun getPersons(@Query("userId") userId: String): Call<List<Person>>


    @GET("Persons")
    fun getPersons(@Query("user_id") userId: String? = null): Call<List<Person>>
}