package com.example.mandatoryassignment.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun LogIn (modifier: Modifier = Modifier) {

    Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Log In", fontSize = 35.sp)

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Username") }
        )

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Password") }
        )

        Row(){

            Button(onClick = { }, modifier = Modifier.padding(5.dp)) {
                Text("Log In")
            }

            Button(onClick = { }, modifier = Modifier.padding(5.dp)) {
                Text("Sign up")
            }

        }









    }


}


@Preview(showBackground = true)
@Composable
fun LogInPreview(){
    LogIn()
}