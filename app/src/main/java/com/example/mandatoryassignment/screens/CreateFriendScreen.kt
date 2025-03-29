package com.example.mandatoryassignment.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import java.util.Calendar
import androidx.compose.ui.platform.LocalContext
import com.example.mandatoryassignment.model.Person
import com.google.firebase.auth.FirebaseUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateFriend(modifier: Modifier = Modifier,
                 navigateBack: () -> Unit = {},
                 user: FirebaseUser? = null,
                 addPerson: (Person) -> Unit = {}) {

    Scaffold(modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Persons") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        })
    {innerPadding ->
        CreateFriendPanel(
            modifier = Modifier.padding(innerPadding),
            navigateBack = navigateBack,
            user = user!!,
            addPerson = addPerson
        )

    }



}


@Composable
private fun CreateFriendPanel(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    user: FirebaseUser,
    addPerson: (Person) -> Unit = {}
){

    var name by rememberSaveable { mutableStateOf("") }
    var day by rememberSaveable { mutableStateOf("") }
    var month by rememberSaveable { mutableStateOf("") }
    var year by rememberSaveable { mutableStateOf("") }
    var remark by rememberSaveable { mutableStateOf("") }

    var nameIsError by rememberSaveable { mutableStateOf(false) }
    var dateIsError by rememberSaveable { mutableStateOf(false) }
    var remarkIsError by rememberSaveable { mutableStateOf(false) }



    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            day = selectedDay.toString()
            month = (selectedMonth + 1).toString() // Month is 0-based
            year = selectedYear.toString()
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )


    Column(modifier = modifier.padding(10.dp).verticalScroll(rememberScrollState())) {

        Text(
            text = "Add new friend",
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it},
            label = { Text("Name") },
            modifier = Modifier.padding(bottom = 16.dp),
            isError = nameIsError
        )

        OutlinedTextField(

             value = if (day.isNotEmpty() && month.isNotEmpty() && year.isNotEmpty())
            {"$day/$month/$year"}
             else {"DD/MM/YYYY"},
            onValueChange = {},
            label = { Text("Select Date") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { datePickerDialog.show() }) {
                    Icon(Icons.Filled.DateRange, contentDescription = "Select Date")
                }
            },
            modifier = Modifier.padding(bottom = 16.dp),
            isError = dateIsError
        )

        OutlinedTextField(
            value = remark,
            onValueChange = { remark = it},
            label = { Text("Remarks") },
            modifier = Modifier.padding(bottom = 16.dp),
            isError = remarkIsError
        )


        Row() {

            Button(onClick = {

                nameIsError = name.isEmpty()
                dateIsError = day.isEmpty() || month.isEmpty() || year.isEmpty()
                remarkIsError = remark.isEmpty()

                if (!nameIsError && !dateIsError && !remarkIsError) {
                    val newPerson = Person(
                        0,
                        user.email!!,
                        name,
                        year.toInt(),
                        month.toInt(),
                        day.toInt(),
                        remark,
                        "",
                        0
                    )
                    Log.d("PersonsRepository", "Adding person: $newPerson")
                    addPerson(newPerson)
                    navigateBack()
                }

            }, modifier = Modifier.padding(end = 8.dp))
            {
                Text("Add")
            }

            Button(onClick = { navigateBack()})
            {
                Text("Cancel")
            }
        }


    }

}




@Preview
@Composable
fun CreateFriendPreview() {
    CreateFriend(
    )
}