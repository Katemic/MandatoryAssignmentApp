package com.example.mandatoryassignment.model



data class Person(val id: Int,
                  val userId: String,
                  val name: String,
                  val birthYear: Int,
                  val birthMonth: Int,
                  val birthDayOfMonth: Int,
                  val remarks: String,
                  val pictureUrl: String,
                  val age: Int ) {
    constructor(userId: String, name: String, birthYear: Int, birthMonth: Int, birthDayOfMonth: Int, remarks: String, pictureUrl: String)
            : this (-1, userId, name, birthYear, birthMonth, birthDayOfMonth, remarks, pictureUrl, 0)


    override fun toString(): String {
        return "Person(id=$id, userId='$userId', name='$name', birthYear=$birthYear, birthMonth=$birthMonth, birthDayOfMonth=$birthDayOfMonth, remarks='$remarks', pictureUrl='$pictureUrl', age=$age)"
    }
}