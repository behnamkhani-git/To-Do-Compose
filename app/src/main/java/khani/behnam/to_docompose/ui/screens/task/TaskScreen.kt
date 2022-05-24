package khani.behnam.to_docompose.ui.screens.task

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import khani.behnam.to_docompose.data.models.Priority
import khani.behnam.to_docompose.data.models.ToDoTask
import khani.behnam.to_docompose.ui.viewmodels.SharedViewModel
import khani.behnam.to_docompose.util.Action

@Composable
fun TaskScreen(
    selectedTask: ToDoTask?,
    sharedViewModel: SharedViewModel,
    navigateToListScree: (Action) -> Unit
) {
    /**
     * When these variables changed, then TaskScreen will be recomposed with new values
     * In fact, we are observing the values
     */
    val title: String by sharedViewModel.title
    val description: String by sharedViewModel.description
    val priority: Priority by sharedViewModel.priority

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScree
            )
        },
        content = {
            TaskContent(
                title = title,
                onTitleChanged = { sharedViewModel.title.value = it },
                description = description,
                onDescriptionChanged = {sharedViewModel.description.value = it},
                priority = priority,
                onPrioritySelected = {sharedViewModel.priority.value = it}
            )
        }
    )
}