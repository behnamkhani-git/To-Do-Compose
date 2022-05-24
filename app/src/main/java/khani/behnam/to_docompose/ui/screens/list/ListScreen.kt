package khani.behnam.to_docompose.ui.screens.list

import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import khani.behnam.to_docompose.R
import khani.behnam.to_docompose.ui.theme.fabBarBackgroundColor
import khani.behnam.to_docompose.ui.viewmodels.SharedViewModel
import khani.behnam.to_docompose.util.SearchAppBarState

@ExperimentalMaterialApi
@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    // is executed once when entered inside the composition. And it is canceled when leaving the composition.
    LaunchedEffect(key1 = true){
        sharedViewModel.getAllTasks()
    }



    // by using collectAsState() we are observing the database
    val allTasks by sharedViewModel.allTasks.collectAsState()

    // Observe the value of searchAppBarState from ViewModel
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState

    // We are observing sharedViewModel.action variable
    val action by sharedViewModel.action

    sharedViewModel.handleDatabaseAction(action)

    Scaffold(
        // Top Bar (Action Bar)
        topBar = {
            ListAppBar(sharedViewModel =  sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState)
        },
        content = {
            ListContent(tasks = allTasks, navigateToTaskScreen = navigateToTaskScreen)
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    )
}

// Creating FAB
@Composable
fun ListFab(
    // Lambda parameter
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(onClick = {
        // -1 Means we want to create a new Task
        onFabClicked(-1)

    }, backgroundColor = MaterialTheme.colors.fabBarBackgroundColor) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
}
