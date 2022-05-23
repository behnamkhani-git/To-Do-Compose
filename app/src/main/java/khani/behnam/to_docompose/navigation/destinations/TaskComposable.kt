package khani.behnam.to_docompose.navigation.destinations

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import khani.behnam.to_docompose.ui.screens.task.TaskScreen
import khani.behnam.to_docompose.util.Action
import khani.behnam.to_docompose.util.Constants
import khani.behnam.to_docompose.util.Constants.LIST_ARGUMENT_KEY
import khani.behnam.to_docompose.util.Constants.LIST_SCREEN
import khani.behnam.to_docompose.util.Constants.TASK_ARGUMENT_KEY
import khani.behnam.to_docompose.util.Constants.TASK_SCREEN

// Extension Function for NavGraphBuilder
fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit
){
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(Constants.TASK_ARGUMENT_KEY){
            type = NavType.IntType
        })
    ){ navBackStackEntry ->
        // note that we don't send the whole Task object,
        // instead we send the id of the Task
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        Log.d("TaskComposable", taskId.toString())
        
        TaskScreen(navigateToListScree = navigateToListScreen)
    }
}