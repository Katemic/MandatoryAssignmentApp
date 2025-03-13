package com.example.mandatoryassignment.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mandatoryassignment.model.Person

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListViewScreen(
    persons: List<Person>,
    modifier: Modifier = Modifier
){

    Scaffold(modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Persons") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
        floatingActionButtonPosition = FabPosition.EndOverlay,
        floatingActionButton = {
            FloatingActionButton(onClick = { /* do something */ }) {
                Icon(Icons.Filled.Add, "Add")
            }
        })
    { innerPadding ->
        PersonListPanel(
            modifier = Modifier.padding(innerPadding),
        )

    }


}

@Composable
private fun PersonListPanel(
    modifier: Modifier = Modifier,
){

}