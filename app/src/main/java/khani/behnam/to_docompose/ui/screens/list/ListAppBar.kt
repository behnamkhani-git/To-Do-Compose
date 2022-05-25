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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import khani.behnam.to_docompose.components.DisplayAlertDialog
import khani.behnam.to_docompose.components.PriorityItem
import khani.behnam.to_docompose.ui.theme.*
import khani.behnam.to_docompose.ui.viewmodels.SharedViewModel
import khani.behnam.to_docompose.util.Action
import khani.behnam.to_docompose.util.SearchAppBarState
import khani.behnam.to_docompose.util.TrailingIconState

@Composable
fun ListAppBar(
    sharedViewModel: SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String
) {
    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                onSearchClicked = {
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.OPENED
                },
                onSortClicked = {},
                // #DELETEALL step 3: Here we define what to do after click yes
                onDeleteAllConfirmed = {
                    sharedViewModel.action.value = Action.DELETE_ALL
                }
            )
        }

        else -> {
            SearchAppBar(
                text = searchTextState,
                onTextChanged = { newText ->
                    sharedViewModel.searchTextState.value = newText
                },
                onCloseClicked = {
                    // #Search step 7: If you clicks on close button is search app bar, then change
                    // state to CLOSED
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.CLOSED
                    sharedViewModel.searchTextState.value = ""
                },
                onSearchClicked = {
                    // #Search step 1: Pass the query to searchDatabase function
                    sharedViewModel.searchDatabase(searchQuery = it)
                })
        }
    }
}

@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllConfirmed: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.list_screen_title),
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        actions = {
            ListAppBarActions(
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteAllConfirmed = onDeleteAllConfirmed
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor
    )
}

@Composable
fun ListAppBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllConfirmed: () -> Unit
) {

    var openDialog by remember {
        mutableStateOf(false)
    }
    
    DisplayAlertDialog(
        title = stringResource(id = R.string.delete_all_tasks), 
        message = stringResource(id = R.string.delete_all_tasks_confirmation),
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        // #DELETEALL step 2: When you click yes, it will run onDeleteAllConfirmed

        onYesClicked = {onDeleteAllConfirmed()}
    )
    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    // #DELETEALL step 1: When you click on delete button, openDialog will set to true

    DeleteAllActions(onDeleteAllConfirmed = {
        openDialog = true
    })
}

@Composable
fun DeleteAllActions(
    // It accepts only one lambda
    onDeleteAllConfirmed: () -> Unit
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
                onDeleteAllConfirmed()
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
    var trailingIconState by remember {
        mutableStateOf(TrailingIconState.READY_TO_DELETE)
    }
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
                text = stringResource(R.string.search_placeholder),
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
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.search_icon),
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    when (trailingIconState) {
                        TrailingIconState.READY_TO_DELETE -> {
                            // Remove the textfield text
                            onTextChanged("")
                            trailingIconState = TrailingIconState.READY_TO_CLOSE
                        }
                        TrailingIconState.READY_TO_CLOSE -> {
                            // If we have text in text field and click on close, it will clear the
                            // textField and then it close the search bar
                            if (text.isNotEmpty()) {
                                onTextChanged("")
                            } else {
                                onCloseClicked()
                                trailingIconState = TrailingIconState.READY_TO_DELETE
                            }
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.close_icon),
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                // Enable search icon in the keyboard
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
    DefaultListAppBar(onSearchClicked = {}, onSortClicked = {}, onDeleteAllConfirmed = {}
    )
}

@Composable
@Preview
private fun SearchAppBarPreview() {
    SearchAppBar(text = "", onTextChanged = {}, onCloseClicked = { }, onSearchClicked = {})
}
