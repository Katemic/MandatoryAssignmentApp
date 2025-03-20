package com.example.mandatoryassignment.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mandatoryassignment.NavRoutes
import com.example.mandatoryassignment.model.Person
import com.google.firebase.auth.FirebaseUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListViewScreen(
    persons: List<Person>,
    modifier: Modifier = Modifier,
    user: FirebaseUser? = null,
    signOut: () -> Unit = {},
    navigateToLogIn: () -> Unit = {}
){

    if (user == null) {
        navigateToLogIn()
    }

    Scaffold(modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Persons") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = { signOut() }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Add")
                    }
                }
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
            persons = persons
        )

    }


}

@Composable
private fun PersonListPanel(
    modifier: Modifier = Modifier,
    persons : List<Person>
){

    val orientation = LocalConfiguration.current.orientation
    val columns = if (orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2
    Row(modifier = modifier.padding(8.dp)){

        LazyVerticalGrid(columns = GridCells.Fixed(columns)) {

            items(persons) { person ->
                Text(person.name + " - " + person.userId)
            }

        }

    }
}


@Preview
@Composable
fun ListPreview() {
    ListViewScreen(listOf(Person("email", "name", 1999, 1, 1, "remarks", "pictureUrl")))

}