package com.example.mandatoryassignment

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mandatoryassignment.model.Person
import com.example.mandatoryassignment.model.PersonsViewModel
import com.example.mandatoryassignment.screens.CreateFriend
import com.example.mandatoryassignment.screens.ListViewScreen
import com.example.mandatoryassignment.screens.LogIn
import com.example.mandatoryassignment.screens.UpdateFriendScreen
import com.example.mandatoryassignment.ui.theme.MandatoryAssignmentTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MandatoryAssignmentTheme {
                MainScreen()
            }
        }
    }
}


@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val personViewmodel : PersonsViewModel = viewModel()
    val persons = personViewmodel.persons.value
    val errorMessage = personViewmodel.errorMessage.value
    val authViewModel: AuthenticationViewModel = viewModel()
    val user = authViewModel.user
    LaunchedEffect(user) {
        user?.let {
            personViewmodel.getPersons(it.email!!)
        }
    }
//    if(user != null){
//        personViewmodel.getPersons(user.email!!)
//    }

    NavHost(navController = navController, startDestination = NavRoutes.LogInScreen.route) {

        composable(NavRoutes.ListViewScreen.route) {
            ListViewScreen(
                persons = persons,
                user = authViewModel.user,
                signOut = authViewModel::signOut,
                navigateToLogIn = { navController.navigate(NavRoutes.LogInScreen.route) },
                navigateToCreateFriend = { navController.navigate(NavRoutes.CreateFriendScreen.route) },
                deletePerson = { id -> personViewmodel.deletePerson(id)},
                onPersonClick =
                    {person -> navController.navigate(NavRoutes.UpdateFriendScreen.route + "/${person.id}")},
                sortByName = {ascending -> personViewmodel.sortByName(ascending)},
                sortByAge = {ascending -> personViewmodel.sortByAge(ascending)},
                sortByBirthday = {ascending -> personViewmodel.sortByBirthday(ascending)},
                filter = {criteria -> personViewmodel.filter(criteria)},
                personReload = {personViewmodel.getPersons(user!!.email!!)},
                isLoading = personViewmodel.isLoadingPersons.value,


            )
        }

        composable(NavRoutes.LogInScreen.route) {
            LogIn(
                user = authViewModel.user,
                message = authViewModel.message,
                signIn = authViewModel::signIn,
                register = authViewModel::register,
                navigateToNextScreen = { navController.navigate(NavRoutes.ListViewScreen.route) }
            )
        }

        composable(NavRoutes.CreateFriendScreen.route) {
            CreateFriend(
                user = authViewModel.user,
                navigateBack = { navController.navigate(NavRoutes.ListViewScreen.route) },
                addPerson = { person ->
                    personViewmodel.createPerson(person)
                }

            )
        }

        composable(NavRoutes.UpdateFriendScreen.route + "/{personId}",
            arguments = listOf(navArgument("personId") { type = NavType.IntType })
        ) { backStackEntry ->
            val personId = backStackEntry.arguments?.getInt("personId")
            var person: Person = persons.find { it.id == personId }!!
            Log.d("PersonsRepository", "PersonId: $personId mainscreen")

                UpdateFriendScreen(
                    navigateBack = { navController.popBackStack() },
                    updatePerson = { id, person -> personViewmodel.updatePerson(id, person) },
                    person = person
                )

        }


    }

}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MandatoryAssignmentTheme {
        Greeting("Android")
    }
}