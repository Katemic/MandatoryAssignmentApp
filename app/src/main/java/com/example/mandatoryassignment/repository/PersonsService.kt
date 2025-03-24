package com.example.mandatoryassignment.repository


import com.example.mandatoryassignment.model.Person
import retrofit2.Call

import retrofit2.http.*


interface PersonsService {
//    @GET("Persons?user_id={userId}")
//    fun getPersons(@Query("userId") userId: String): Call<List<Person>>


    @GET("Persons")
    fun getPersons(@Query("user_id") userId: String? = null): Call<List<Person>>

    @GET("Persons/{id}")
    fun getPersonById(@Path("id") id: Int): Call<Person>

    @POST("Persons")
    fun createPerson(@Body person: Person): Call<Person>

    @DELETE("Persons/{id}")
    fun deletePerson(@Path("id") id: Int): Call<Person>

    @PUT("Persons/{id}")
    fun updatePerson(@Path("id") id: Int, @Body person: Person): Call<Person>

}