package khani.behnam.to_docompose.navigation.destinations

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import khani.behnam.to_docompose.ui.screens.task.TaskScreen
import khani.behnam.to_docompose.ui.viewmodels.SharedViewModel
import khani.behnam.to_docompose.util.Action
import khani.behnam.to_docompose.util.Constants
import khani.behnam.to_docompose.util.Constants.LIST_ARGUMENT_KEY
import khani.behnam.to_docompose.util.Constants.LIST_SCREEN
import khani.behnam.to_docompose.util.Constants.TASK_ARGUMENT_KEY
import khani.behnam.to_docompose.util.Constants.TASK_SCREEN

// Extension Function for NavGraphBuilder
fun NavGraphBuilder.taskComposable(

    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit
) {
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(Constants.TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        // note that we don't send the whole Task object,
        // instead we send the id of the Task
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        sharedViewModel.getSelectedTask(taskId = taskId)
        val selectedTask by sharedViewModel.selectedTask.collectAsState()

        // When we move from here to TaskScreen, then we will set values of the selected Task
        // so that we can use it to fill TaskScreen (TaskComponent) Fields

        /**
         * LaunchedEffect will execute its block when key1 value changes,
         * it means when selectedTask changes at line 36:
         * val selectedTask by sharedViewModel.selectedTask.collectAsState()
         * then it will call updateTaskFields
         */
        LaunchedEffect(key1 = selectedTask) {
            if (selectedTask != null || taskId == -1){
                sharedViewModel.updateTaskFields(selectedTask = selectedTask)
            }
        }

        TaskScreen(
            selectedTask = selectedTask,
            sharedViewModel = sharedViewModel,
            navigateToListScree = navigateToListScreen
        )
    }
}