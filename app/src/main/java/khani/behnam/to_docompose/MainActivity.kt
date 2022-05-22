package khani.behnam.to_docompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import khani.behnam.to_docompose.navigation.SetupNavigation
import khani.behnam.to_docompose.ui.theme.ToDoComposeTheme
import khani.behnam.to_docompose.ui.viewmodels.SharedViewModel

@AndroidEntryPoint /* This is an annotation that inject dependencies into Activities or
Fragments or Views */
class MainActivity : ComponentActivity() {
    // The navigation host is an empty container where destinations are swapped in and out as a user
    // navigates through your app
    // NavController manages app navigation within a NavHost
    private lateinit var navController: NavHostController

    // Init SharedViewModel and use it in different parts of the app
    private val sharedViewModel: SharedViewModel by viewModels()
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoComposeTheme {
                navController = rememberNavController()
                SetupNavigation(navController = navController, sharedViewModel)
            }
        }
    }
}

