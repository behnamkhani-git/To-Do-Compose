package khani.behnam.to_docompose.ui.screens.task

import android.content.Context
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
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

    // Get context in Jetpack Compose
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = { action ->
                    if (action == Action.NO_ACTION) { // If user opens TaskScreen and do nothing and click on close or back
                        navigateToListScree(action)
                    } else { // If user has something in TaskScreen, for example updates or added or deleted a Task
                        if (sharedViewModel.validateFields()) {
                            navigateToListScree(action)
                        } else {
                            displayToast(context = context)
                        }
                    }
                }
            )
        },
        content = {
            TaskContent(
                title = title,
                onTitleChanged = { sharedViewModel.updateTitle(it) },
                description = description,
                onDescriptionChanged = { sharedViewModel.description.value = it },
                priority = priority,
                onPrioritySelected = { sharedViewModel.priority.value = it }
            )
        }
    )
}

fun displayToast(context: Context) {
    Toast.makeText(context, "Fields are empty!", Toast.LENGTH_SHORT).show()
}
