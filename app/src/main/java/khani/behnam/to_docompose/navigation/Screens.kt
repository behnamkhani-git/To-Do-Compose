package khani.behnam.to_docompose.navigation

import androidx.navigation.NavHostController
import khani.behnam.to_docompose.util.Action
import khani.behnam.to_docompose.util.Constants.LIST_SCREEN

class Screens(navController: NavHostController) {
    val list: (Action) -> Unit = { action ->
        navController.navigate("list/${action.name}"){
            popUpTo(LIST_SCREEN){
                inclusive = true
            }
        }
    }
    val task: (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }
}