package com.example.mandatoryassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mandatoryassignment.model.PersonsViewModel
import com.example.mandatoryassignment.screens.ListViewScreen
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
    val viewmodel : PersonsViewModel = viewModel()
    val persons = viewmodel.persons.value
    val errorMessage = viewmodel.errorMessage.value

    NavHost(navController = navController, startDestination = NavRoutes.ListViewScreen.route) {

        composable(NavRoutes.ListViewScreen.route) {
            ListViewScreen(
                persons = persons
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