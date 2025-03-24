package com.example.mandatoryassignment.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.mandatoryassignment.model.Person

@Composable
fun UpdateFriendScreen(modifier: Modifier = Modifier,
                       personId: Int,
                       getById: (id: Int) -> Person,
                       navigateBack: () -> Unit = {},
                       updatePerson: (Int, Person) -> Unit = {id: Int, person: Person -> })
{
    var person by remember { mutableStateOf<Person?>(null) }

    LaunchedEffect(personId) {
        person = getById(personId)
    }


}