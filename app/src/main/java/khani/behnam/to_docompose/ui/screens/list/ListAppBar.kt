package khani.behnam.to_docompose.ui.screens.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import khani.behnam.to_docompose.R
import khani.behnam.to_docompose.data.models.Priority
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import khani.behnam.to_docompose.components.PriorityItem
import khani.behnam.to_docompose.ui.theme.*

@Composable
fun ListAppBar(onSearchClicked: () -> Unit) {
//    DefaultListAppBar(
//        onSearchClicked = {},
//        onSortClicked = {},
//        onDeleteClicked = {}
//    )
    SearchAppBar(text = "Search", onTextChanged = {}, onCloseClicked = { }, onSearchClicked = {})
}

@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = "Tasks", color = MaterialTheme.colors.topAppBarContentColor)
        },
        actions = {
            ListBarActions(
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteClicked = onDeleteClicked
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor
    )
}

@Composable
fun ListBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit
) {
    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAllActions(onDeleteClicked = onDeleteClicked)
}

@Composable
fun DeleteAllActions(
    // It accepts only one lambda
    onDeleteClicked: () -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    IconButton(onClick = { expanded = true }) {

        Icon(
            painter = painterResource(id = R.drawable.ic_vertical_menu),
            contentDescription = stringResource(id = R.string.delete_all_action),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(onClick = {
                expanded = false
                onDeleteClicked()
            }) {
                Text(
                    modifier = Modifier.padding(start = LARGE_PADDING),
                    text = stringResource(id = R.string.delete_all_action),
                    style = Typography.subtitle2
                )
            }
        }
    }
}

@Composable
fun SortAction(
    onSortClicked: (Priority) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(onClick = {
        // Opens the menu
        expanded = true
    }) {
        // painter is used for XML files
        Icon(
            painter = painterResource(id = R.drawable.ic_filter_list),
            contentDescription = stringResource(id = R.string.sort_action),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
        DropdownMenu(expanded = expanded, onDismissRequest = {
            expanded = false
        }) {
            DropdownMenuItem(onClick = {
                expanded = false
            }) {
                PriorityItem(priority = Priority.LOW)
                onSortClicked(Priority.LOW)
            }
            DropdownMenuItem(onClick = {
                expanded = false
            }) {
                PriorityItem(priority = Priority.HIGH)
                onSortClicked(Priority.HIGH)

            }
            DropdownMenuItem(onClick = {
                expanded = false
            }) {
                PriorityItem(priority = Priority.NONE)
                onSortClicked(Priority.NONE)

            }
        }
    }
}

@Composable
fun SearchAction(onSearchClicked: () -> Unit) {

        IconButton(onClick = { onSearchClicked() }) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(id = R.string.search_action),
                // Change color of sort icon
                tint = MaterialTheme.colors.topAppBarContentColor
            )
        }
    }
    @Composable
    fun SearchAppBar(
        text: String, // text value on the search field
        onTextChanged: (String) -> Unit,  //
        onCloseClicked: () -> Unit, // when we close search app bar
        onSearchClicked: (String) -> Unit // when we click on search icon
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(TOP_APP_BAR_HEIGHT),
            elevation = AppBarDefaults.TopAppBarElevation,
            color = MaterialTheme.colors.topAppBarBackgroundColor
        ) {
            TextField(modifier = Modifier.fillMaxWidth(), value = text, onValueChange = {
                onTextChanged(it)
            }, placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.disabled),
                    text = "Search",
                    color = Color.White
                )
            },
                textStyle = TextStyle(
                    color = MaterialTheme.colors.topAppBarContentColor,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize
                ),
                singleLine = true,
                leadingIcon = {
                    IconButton(modifier = Modifier.alpha(ContentAlpha.disabled), onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.Search, contentDescription = "Search Icon",
                            tint = MaterialTheme.colors.topAppBarContentColor
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = { onCloseClicked() }) {
                        Icon(
                            imageVector = Icons.Filled.Close, contentDescription = "Close Icon",
                            tint = MaterialTheme.colors.topAppBarContentColor
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    // Enable serach icon in the keyboard
                    imeAction = ImeAction.Search
                ),
                // Whenever user press the Search icon in the keyboard then trigger onSearchClicked
                keyboardActions = KeyboardActions(onSearch = {
                    onSearchClicked(text)
                }) {

                },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = MaterialTheme.colors.topAppBarContentColor,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = Color.Transparent
                )

            )
        }
    }


    @Composable
    @Preview
    private fun DefaultListAppBarPreview() {
        DefaultListAppBar(onSearchClicked = {}, onSortClicked = {}, onDeleteClicked = {}
        )
    }

    @Composable
    @Preview
    private fun SearchAppBarPreview() {
        SearchAppBar(text = "", onTextChanged = {}, onCloseClicked = { }, onSearchClicked = {})
    }
