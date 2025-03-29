package com.example.mandatoryassignment.screens

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mandatoryassignment.model.Person
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateFriendScreen(modifier: Modifier = Modifier,
                       navigateBack: () -> Unit = {},
                       updatePerson: (Int, Person) -> Unit = {id: Int, person: Person -> },
                       person: Person)
{

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


        Box(modifier = Modifier.padding(innerPadding)) {
            UpdatePerson(
                modifier = Modifier,
                person = person,
                navigateBack = navigateBack,
                updatePerson = updatePerson
            )
        }

    }

}


@Composable
private fun UpdatePerson(
    modifier: Modifier = Modifier,
    person: Person,
    navigateBack: () -> Unit,
    updatePerson: (Int, Person) -> Unit = {id: Int, person: Person -> })
{

    var name by rememberSaveable { mutableStateOf(person.name)}
    var day by rememberSaveable { mutableIntStateOf(person.birthDayOfMonth)}
    var month by rememberSaveable { mutableIntStateOf(person.birthMonth)}
    var year by rememberSaveable { mutableIntStateOf(person.birthYear)}
    var remark by rememberSaveable { mutableStateOf(person.remarks)}

    var nameIsError by rememberSaveable { mutableStateOf(false) }
    var dateIsError by rememberSaveable { mutableStateOf(false) }
    var remarkIsError by rememberSaveable { mutableStateOf(false) }


    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            day = selectedDay
            month = selectedMonth + 1 // Month is 0-based
            year = selectedYear
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )



    Column(modifier = modifier) {

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            isError = nameIsError,
        )


        OutlinedTextField(
            value = if (day.toString().isNotEmpty() && month.toString().isNotEmpty() && year.toString().isNotEmpty())
                "$day/$month/$year"
            else
                "DD/MM/YYYY",
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
            value = if (remark.isNullOrEmpty()) "" else remark,
            onValueChange = { remark = it },
            label = { Text("Remarks") },
            isError = remarkIsError
        )



        Row() {
            Button(onClick = {
                nameIsError = name.isEmpty()
                remarkIsError = remark.isEmpty()
                dateIsError = day == 0 || month == 0 || year == 0

                if (!nameIsError && !dateIsError && !remarkIsError) {
                    updatePerson(
                        person.id,
                        Person(
                            person.id,
                            person.userId,
                            name,
                            year,
                            month,
                            day,
                            remark,
                            person.pictureUrl,
                            person.age
                        )
                    )
                    navigateBack()
                }
            }, modifier = Modifier.padding(end = 8.dp)) {
                Text("Update")
            }

            Button(onClick = { navigateBack() }) {
                Text("Cancel")
            }

        }
    }


}