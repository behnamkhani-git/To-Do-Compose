package khani.behnam.to_docompose.ui.screens.list
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import khani.behnam.to_docompose.R
import khani.behnam.to_docompose.ui.theme.fabBarBackgroundColor

@Composable
fun ListScreen(navigateToTaskScreen: (taskId: Int) -> Unit) {
    Scaffold(
        // Top Bar (Action Bar)
        topBar = {
            ListAppBar(onSearchClicked={})
        },

        content = {},
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    )
}

// Creating FAB
@Composable
fun ListFab(
    // Lambda parameter
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(onClick = {
        // -1 Means we want to create a new Task
        onFabClicked(-1)

    }, backgroundColor = MaterialTheme.colors.fabBarBackgroundColor) {
        Icon(imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
}

@Composable
@Preview
private fun ListScreenPreview() {
    ListScreen(navigateToTaskScreen = {})
}