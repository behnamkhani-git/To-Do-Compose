package khani.behnam.to_docompose.ui.screens.task

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import khani.behnam.to_docompose.data.models.Priority
import khani.behnam.to_docompose.data.models.ToDoTask
import khani.behnam.to_docompose.util.Action

@Composable
fun TaskScreen(
    selectedTask: ToDoTask?,
    navigateToListScree: (Action) -> Unit
) {
    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScree
            )
        },
        content = {
            TaskContent(
                title = "",
                onTitleChanged = {},
                description = "",
                onDescriptionChanged = {},
                priority = Priority.LOW,
                onPrioritySelected = {}
            )
        }
    )
}