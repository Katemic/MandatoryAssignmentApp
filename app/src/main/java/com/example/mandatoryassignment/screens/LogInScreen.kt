package com.example.mandatoryassignment.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseUser


@Composable
fun LogIn (modifier: Modifier = Modifier,
           user: FirebaseUser? = null,
           message: String = "",
           signIn: (email: String, password: String) -> Unit = { _, _ -> },
           register : (email: String, password: String) -> Unit = { _, _ -> },
           navigateToNextScreen: () -> Unit = {})
{

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(user) {
        if (user != null) {
            navigateToNextScreen()
        }
    }


        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Log In", fontSize = 35.sp)

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Username") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation =
                        if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            if (showPassword) {
                                Icon(Icons.Filled.Clear, contentDescription = "Hide password")
                            } else {
                                Icon(
                                    Icons.Filled.Lock,
                                    contentDescription = "Show password"
                                )
                            }
                        }
                    },
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })

                )

                Row() {

                    Button(
                        onClick = { signIn(email, password)
                            keyboardController?.hide()},
                        modifier = Modifier.padding(5.dp),
                        enabled = email.isNotEmpty() && password.isNotEmpty()
                    ) {
                        Text("Log In")
                    }

                    Button(onClick = { register(email, password) },
                        modifier = Modifier.padding(5.dp),
                        enabled = email.isNotEmpty() && password.isNotEmpty()) {
                        Text("Sign up")
                    }


                }

                Row(){
                    Text(message,
                        color = androidx.compose.ui.graphics.Color.Red)
                }


            }
        }

}


@Preview(showBackground = true)
@Composable
fun LogInPreview(){
    LogIn()
}