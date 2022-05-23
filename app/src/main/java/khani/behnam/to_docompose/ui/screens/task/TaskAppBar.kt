package khani.behnam.to_docompose.ui.screens.task

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Delete
import khani.behnam.to_docompose.R
import khani.behnam.to_docompose.data.models.Priority
import khani.behnam.to_docompose.data.models.ToDoTask
import khani.behnam.to_docompose.ui.theme.topAppBarBackgroundColor
import khani.behnam.to_docompose.ui.theme.topAppBarContentColor
import khani.behnam.to_docompose.util.Action

@Composable
fun TaskAppBar(navigateToListScree: (Action) -> Unit) {
    NewTaskAppBar(navigateToListScree)
}

@Composable
fun NewTaskAppBar(navigateToListScree: (Action) -> Unit) {
    TopAppBar(
        navigationIcon = {
            // back arrow
            BackAction(onBackClicked = navigateToListScree)
        },
        title = {
            Text(
                text = stringResource(R.string.add_task),
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            AddAction(onAddClicked = navigateToListScree)
        }
    )
}

// Back to list screen with Action.NO_ACTION
@Composable
fun BackAction(onBackClicked: (Action) -> Unit) {
    // we want to go back to the list screen and no action happened
    IconButton(onClick = { onBackClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back_arrow),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

// Add a new task and back to list screen with Action.ADD
@Composable
fun AddAction(onAddClicked: (Action) -> Unit) {
    // we want to go back to the list screen and no action happened
    IconButton(onClick = { onAddClicked(Action.ADD) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.add_task),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun ExistingTaskAppBar(
    selectedTask: ToDoTask,
    navigateToListScree: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            CloseAction(onCloseClicked = navigateToListScree)
        },
        title = {
            Text(
                text = selectedTask.title,
                color = MaterialTheme.colors.topAppBarContentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            DeleteAction(onDeleteAction = navigateToListScree)
            UpdateAction(onUpdateClicked = navigateToListScree)
        }
    )
}

// Back to list screen with Action.NO_ACTION
@Composable
fun CloseAction(onCloseClicked: (Action) -> Unit) {
    // we want to go back to the list screen and no action happened
    IconButton(onClick = { onCloseClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(id = R.string.close_icon),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

// Back to list screen with Action.NO_ACTION
@Composable
fun DeleteAction(onDeleteAction: (Action) -> Unit) {
    // we want to go back to the list screen and no action happened
    IconButton(onClick = { onDeleteAction(Action.DELETE) }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(R.string.delete_icon),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

// Back to list screen with Action.NO_ACTION
@Composable
fun UpdateAction(onUpdateClicked: (Action) -> Unit) {
    // we want to go back to the list screen and no action happened
    IconButton(onClick = { onUpdateClicked(Action.UPDATE) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(R.string.update_icon),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
@Preview
private fun NewTaskAppBarPreview() {
    NewTaskAppBar(navigateToListScree = {})
}

@Composable
@Preview
private fun ExistingTaskAppBarPreview() {
    ExistingTaskAppBar(
        selectedTask = ToDoTask(
            id = 0,
            title = "Go to meeting",
            description = "I have a meeting with Joe at 9 o'clock",
            priority = Priority.HIGH
        ),
        navigateToListScree = {})
}