package com.example.mandatoryassignment.screens

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mandatoryassignment.NavRoutes
import com.example.mandatoryassignment.model.Person
import com.google.firebase.auth.FirebaseUser
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.LaunchedEffect


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListViewScreen(
    persons: List<Person>,
    modifier: Modifier = Modifier,
    user: FirebaseUser? = null,
    signOut: () -> Unit = {},
    navigateToLogIn: () -> Unit = {},
    navigateToCreateFriend: () -> Unit = {},
    deletePerson: (id : Int) -> Unit = {},
    onPersonClick: (Person) -> Unit = {},
    sortByName: (ascending: Boolean) -> Unit = {},
    sortByAge: (ascending: Boolean) -> Unit = {},
    sortByBirthday: (ascending: Boolean) -> Unit = {},
    filter: (criteria: String) -> Unit = {},
    personReload: () -> Unit = {},
    isLoading: Boolean
){

    LaunchedEffect(user){
        if (user == null) {
            navigateToLogIn()
        }
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
            FloatingActionButton(onClick = { navigateToCreateFriend()}) {
                Icon(Icons.Filled.Add, "Add")
            }
        })
    { innerPadding ->
        PersonListPanel(
            modifier = Modifier.padding(innerPadding),
            persons = persons,
            deletePerson = deletePerson,
            onPersonClick = onPersonClick,
            sortByName = sortByName,
            sortByAge = sortByAge,
            sortByBirthday = sortByBirthday,
            filter = filter,
            personReload = personReload,
            isLoading = isLoading

        )

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PersonListPanel(
    modifier: Modifier = Modifier,
    persons: List<Person>,
    onPersonClick: (Person) -> Unit = {},
    deletePerson: (id: Int) -> Unit = {},
    sortByName: (ascending: Boolean) -> Unit = {},
    sortByAge: (ascending: Boolean) -> Unit = {},
    sortByBirthday: (ascending: Boolean) -> Unit = {},
    filter: (criteria: String) -> Unit = {},
    personReload: () -> Unit = {},
    isLoading: Boolean
) {

    val orientation = LocalConfiguration.current.orientation
    val columns = if (orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2

    var sortNameAscending by remember { mutableStateOf(true) }
    var sortAgeAscending by remember { mutableStateOf(true) }
    var sortBirthdayAscending by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) } //husk den skal være false
    var filterCriteria by remember { mutableStateOf("") }




    Column(modifier = modifier.padding(3.dp)) {
        Card(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(bottom = 7.dp)
                .padding(top = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            ))
        {


            Text(
                text = "Sorting options",
                modifier = Modifier.padding(13.dp)
            )



            if (expanded) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 12.dp)
                ) {

                    Text("Sort by: ")

                    Button(onClick = {
                        sortByName(sortNameAscending)
                        sortNameAscending = !sortNameAscending
                    }) {
                        Text("Name")
                    }

                    Button(onClick = {
                        sortByAge(sortAgeAscending)
                        sortAgeAscending = !sortAgeAscending
                    }) {
                        Text("Age")
                    }
                    Button(onClick = {
                        sortByBirthday(sortBirthdayAscending)
                        sortBirthdayAscending = !sortBirthdayAscending
                    }) {
                        Text("Birthday")
                    }
                }
                Row(
                    modifier = Modifier.padding(7.dp),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    TextField(
                        value = filterCriteria,
                        onValueChange = { filterCriteria = it },
                        label = { Text("Filter by name or age") },
                        modifier = Modifier.width(240.dp).padding(8.dp)
                    )

                    Button(
                        onClick = { filter(filterCriteria) },
                        enabled = filterCriteria.isNotEmpty(),
                        modifier = Modifier.padding(start = 3.dp)
                    ) {
                        Text("Filter")
                    }

                }
            }
        }


        PullToRefreshBox(
            isRefreshing = isLoading,
            onRefresh = { personReload() }

        ) {


            Column {

                Row {

                    LazyVerticalGrid(columns = GridCells.Fixed(columns)) {

                        items(persons) { person ->
                            FriendItem(
                                person = person,
                                deletePerson = deletePerson,
                                onPersonClick = onPersonClick
                            )
                        }

                    }

                }
            }
        }

    }
}

@Composable
private fun FriendItem(
    person : Person,
    modifier: Modifier = Modifier,
    onPersonClick: (Person) -> Unit = {},
    deletePerson: (id : Int) -> Unit = {}
){

    var expanded by rememberSaveable { mutableStateOf(false) }
    var showDialog by rememberSaveable { mutableStateOf(false) }

    Card(modifier = modifier
        .padding(4.dp)
        .fillMaxSize()
        .clickable { expanded = !expanded }
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
                    Button(onClick = { onPersonClick(person) },
                        modifier = Modifier.padding(end = 8.dp)) {
                        Text("Edit")
                    }
                    Button(onClick = { showDialog = true }) {
                        Text("Delete")
                    }
                }

            }
        }
    }

    if (showDialog) {
        DeleteDialog(
            onDismissRequest = { showDialog = false },
            onConfirm = {
                deletePerson(person.id)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }

}

@Composable
private fun DeleteDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Dismiss")
            }
        },
        title = {
            Text("Delete friend?")
        },
        text = {
            Text("Are you sure you want to delete this item?")
        },
        modifier = modifier
    )
}




@Preview
@Composable
fun ListPreview() {
    ListViewScreen(listOf(Person("email", "name", 1999, 1, 1, "remarks", "pictureUrl")),
        isLoading = false)

}