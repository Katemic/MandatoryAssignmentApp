package com.example.mandatoryassignment.screens

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
                FriendItem(person = person)
            }

        }

    }
}

@Composable
private fun FriendItem(
    person : Person,
    modifier: Modifier = Modifier,
    onPersonClick: (Person) -> Unit = {},
    onPersonDelete: (Person) -> Unit = {}
){

    var expanded by remember { mutableStateOf(false) }

    Card(modifier = modifier.padding(4.dp).fillMaxSize().clickable { expanded = !expanded }
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = person.name + " - " + person.birthDayOfMonth + "/" + person.birthMonth + "/" + person.birthYear
                )

            }
            if (expanded) {

                Text(
                    text = "age: " + person.age,
                )

                Text(
                    text = "remarks: " + person.remarks,
                )

                Row() {
                    Button(onClick = { TODO() }) {
                        Text("Edit")
                    }
                    Button(onClick = { TODO() }) {
                        Text("Delete")
                    }
                }

            }
        }
    }

}


@Preview
@Composable
fun ListPreview() {
    ListViewScreen(listOf(Person("email", "name", 1999, 1, 1, "remarks", "pictureUrl")))

}