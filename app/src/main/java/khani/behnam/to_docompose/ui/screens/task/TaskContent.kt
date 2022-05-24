package khani.behnam.to_docompose.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import khani.behnam.to_docompose.R
import khani.behnam.to_docompose.components.PriorityDropDown
import khani.behnam.to_docompose.data.models.Priority
import khani.behnam.to_docompose.ui.theme.LARGE_PADDING
import khani.behnam.to_docompose.ui.theme.MEDIUM_PADDING

@Composable
fun TaskContent(
    title: String,
    onTitleChanged: (String) -> Unit,
    description: String,
    onDescriptionChanged: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(LARGE_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { onTitleChanged(it) },
            label = { Text(text = stringResource(R.string.title)) },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true
        )
        Divider(
            modifier = Modifier.height(MEDIUM_PADDING),
            color = MaterialTheme.colors.background
        )
        PriorityDropDown(
            priority = priority,
            onPrioritySelected = onPrioritySelected
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = { onDescriptionChanged(it) },
            label = {
                Text(text = stringResource(R.string.description))
            },
            textStyle = MaterialTheme.typography.body1
        )
    }
}

@Composable
@Preview
private fun TaskContentPreview(){
    TaskContent(
        title = "",
        onTitleChanged = {},
        description = "",
        onDescriptionChanged = {},
        priority = Priority.LOW,
        onPrioritySelected = {}
    )
}