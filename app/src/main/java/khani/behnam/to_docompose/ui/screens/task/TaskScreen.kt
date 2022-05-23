package khani.behnam.to_docompose.ui.screens.task

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import khani.behnam.to_docompose.util.Action

@Composable
fun TaskScreen(navigateToListScree: (Action) -> Unit) {
    Scaffold(
        topBar = {
            TaskAppBar(navigateToListScree = navigateToListScree)
        },
        content = {

        }
    )
}