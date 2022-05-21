package khani.behnam.to_docompose.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import khani.behnam.to_docompose.ui.screens.list.ListScreen
import khani.behnam.to_docompose.util.Constants.LIST_ARGUMENT_KEY
import khani.behnam.to_docompose.util.Constants.LIST_SCREEN

// Extension Function for NavGraphBuilder
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit
    ){
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY){
            type = NavType.StringType
        })
    ){
        ListScreen(navigateToTaskScreen = navigateToTaskScreen)
    }
}