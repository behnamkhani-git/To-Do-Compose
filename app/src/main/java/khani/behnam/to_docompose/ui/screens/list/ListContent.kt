package khani.behnam.to_docompose.ui.screens.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import khani.behnam.to_docompose.data.models.Priority
import khani.behnam.to_docompose.data.models.ToDoTask
import khani.behnam.to_docompose.ui.theme.*
import khani.behnam.to_docompose.util.RequestState
import khani.behnam.to_docompose.util.SearchAppBarState

@ExperimentalMaterialApi
@Composable
fun ListContent(
    allTasks: RequestState<List<ToDoTask>>,
    searchedTasks: RequestState<List<ToDoTask>>,
    searchAppBarState: SearchAppBarState,

    // a lambda that takes a task id and returns nothing
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    // #Search step 6: Runs this block, that Triggered means user clicked on search icon
    // We have set this state to Triggered in #Search step 2.1
    if (searchAppBarState == SearchAppBarState.TRIGGERED) {
        // TRIGGERED is when the user click on search button

        if (searchedTasks is RequestState.Success) {
            HandleListContent(
                tasks = searchedTasks.data,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }

    } else {
        if (allTasks is RequestState.Success) {
            HandleListContent(tasks = allTasks.data, navigateToTaskScreen = navigateToTaskScreen)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun HandleListContent(
    tasks: List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (tasks.isEmpty()) {
        EmptyContent()  // Show the sad icon
    } else {
        DisplayTasks(tasks, navigateToTaskScreen)  // or display the found tasks
    }
}

@ExperimentalMaterialApi
@Composable
fun DisplayTasks(
    tasks: List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    LazyColumn {
        items(items = tasks,
            key = { task ->
                // id of each row in our lazy column (recyclerview!)
                task.id
            }
        ) { task ->
            TaskItem(toDoTask = task, navigateToTaskScreen = navigateToTaskScreen)
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun TaskItem(
    toDoTask: ToDoTask,
    // a lambda that takes a task id and returns nothing
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    // Surface is like a background
    Surface(modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.taskItemBackgroundColor,
        shape = RectangleShape,
        elevation = TASK_ITEM_ELEVATION,
        onClick = {
            navigateToTaskScreen(toDoTask.id)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = LARGE_PADDING)
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(8f),
                    text = toDoTask.title,
                    color = MaterialTheme.colors.taskItemTextColor,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier
                            .width(PRIORITY_INDICATOR_SIZE)
                            .height(PRIORITY_INDICATOR_SIZE)
                    ) {
                        drawCircle(color = toDoTask.priority.color)
                    }
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = toDoTask.description,
                color = MaterialTheme.colors.taskItemTextColor,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis  // displays ... in the end of the text
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
@Preview
fun taskItemPreview() {
    TaskItem(
        toDoTask = ToDoTask(
            0, "Title",
            "Some random textSome random textSome random textSome random textSome random textSome random text",
            Priority.MEDIUM
        ),
        navigateToTaskScreen = {}
    )
}