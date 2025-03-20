package com.example.mandatoryassignment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class AuthenticationViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    var user: FirebaseUser? by mutableStateOf(auth.currentUser)
    var message by mutableStateOf("")

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {task ->
                    if (task.isSuccessful) {
                        user = auth.currentUser
                        message = ""
                    } else {
                        user = null
                        message = task.exception?.message ?: "Unknown error"
                    }
                }
        }
    }


    fun signOut() {
        viewModelScope.launch {
            user = null
            auth.signOut()
        }
    }


    fun register(email: String, password: String) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {task ->
                    if (task.isSuccessful) {
                        user = auth.currentUser
                        message = ""
                    }
                    else {
                        user = null
                        message = task.exception?.message ?: "Unknown error"
                    }
                }

        }
    }


}