package khani.behnam.to_docompose.ui.screens.list

import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import khani.behnam.to_docompose.R
import khani.behnam.to_docompose.ui.theme.fabBarBackgroundColor
import khani.behnam.to_docompose.ui.viewmodels.SharedViewModel
import khani.behnam.to_docompose.util.Action
import khani.behnam.to_docompose.util.SearchAppBarState
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    // is executed once when entered inside the composition. And it is canceled when leaving the composition.
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
    }


    // by using collectAsState() we are observing the database
    val allTasks by sharedViewModel.allTasks.collectAsState()

    // by using collectAsState() we are observing the database
    // #Search step 4: Founded tasks are observing here, so any changes to step 3, reflects here
    val searchedTasks by sharedViewModel.searchedTasks.collectAsState()

    // Observe the value of searchAppBarState from ViewModel
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState

    // We are observing sharedViewModel.action variable
    val action by sharedViewModel.action


    val scaffoldState = rememberScaffoldState()
    // Whenever our listscreen triggers, will call this function
    DisplaySnackBar(
        scaffoldState = scaffoldState,
        handleDatabaseActions = { sharedViewModel.handleDatabaseAction(action = action) },
        onUndoClicked = {
            sharedViewModel.action.value = it
        },
        taskTitle = sharedViewModel.title.value,
        action = action
    )

    Scaffold(
        // Display the Snackbar
        scaffoldState = scaffoldState,
        // Top Bar (Action Bar)
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )
        },
        content = {
            // #Search step 5: Trigger ListContent
            ListContent(
                allTasks = allTasks,
                searchedTasks = searchedTasks,
                searchAppBarState = searchAppBarState,
                navigateToTaskScreen = navigateToTaskScreen
            )
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

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    handleDatabaseActions: () -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle: String,
    action: Action
) {
    handleDatabaseActions()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = "${action.name}: $taskTitle",
                    actionLabel = setActionLabel(action)
                )
                undoDeletedTask(
                    action = action,
                    snackbarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
        }
    }
}

private fun setActionLabel(action: Action): String {
    return if (action.name == "DELETE") {
        "UNDO"
    } else {
        "OK"
    }
}

private fun undoDeletedTask(
    action: Action,
    snackbarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackbarResult == SnackbarResult.ActionPerformed
        && action == Action.DELETE
    ) {
        onUndoClicked(Action.UNDO)
    }
}