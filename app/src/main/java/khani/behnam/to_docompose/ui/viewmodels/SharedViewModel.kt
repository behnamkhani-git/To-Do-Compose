package khani.behnam.to_docompose.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import khani.behnam.to_docompose.data.models.Priority
import khani.behnam.to_docompose.data.models.ToDoTask
import khani.behnam.to_docompose.data.repositories.DataStoreRepository
import khani.behnam.to_docompose.data.repositories.ToDoRepository
import khani.behnam.to_docompose.util.Action
import khani.behnam.to_docompose.util.Constants
import khani.behnam.to_docompose.util.Constants.MAX_TITLE_LENGTH
import khani.behnam.to_docompose.util.RequestState
import khani.behnam.to_docompose.util.SearchAppBarState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
To enable injection of a ViewModel by Hilt use the @HiltViewModel annotation
 */
@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: ToDoRepository,
    private val dataStoreRepository: DataStoreRepository  // Inject DataStoreRepository inside sharedViewModel
) : ViewModel() {

    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)


    /**
    We use the following variables to hold changes at TaskContent.kt
     */
    val id: MutableState<Int> = mutableStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)

    // When we click on search icon, this will be opened
    val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)

    // Set the text of search box
    val searchTextState: MutableState<String> = mutableStateOf("")
    /*
    Flow vs LiveData:
    Flows are based on Coroutines by default.
    Also you can use powerful operators like map and filter in flows
    As they use coroutines, so flows are more testable.

    Flows are hot and cold.
    StateFlow is hot. it means it keep emitting values even if there are no collectors
    A cold flow means it would not emit anything if there is no collector.
     */

    // We are wrapping the response of _allTasks to be RequestState
    private val _allTasks =
        MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle /*default value is an empty list */)

    /* This is publicly exposed for our composable */
    val allTasks: StateFlow<RequestState<List<ToDoTask>>> = _allTasks

    // We are wrapping the response of _searchedTasks to be RequestState
    private val _searchedTasks =
        MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle /*default value is .Idle */)
    /* This is publicly exposed for our composable */
    // #Search step 3: After assigning the searched items to _searchedTasks, then
    // searchedTasks will observe changes (by founded tasks)

    val searchedTasks: StateFlow<RequestState<List<ToDoTask>>> = _searchedTasks


    fun searchDatabase(searchQuery: String) {
        // #Search step 2: Update _searchedTasks with the result of search on database

        _searchedTasks.value = RequestState.Loading
        /*
        A ViewModelScope is defined for each ViewModel in your app. Any coroutine launched in this
        scope is automatically canceled if the ViewModel is cleared. Coroutines are useful here for
        when you have work that needs to be done only if the ViewModel is active.
         */
        try {
            viewModelScope.launch {
                repository.searchDatabase(searchQuery = "%$searchQuery%")
                    .collect { searchedTasks ->
                        _searchedTasks.value = RequestState.Success(searchedTasks)
                    }

            }
        } catch (e: Exception) {
            _searchedTasks.value = RequestState.Error(e)
        }
        // TRIGGERED is when the user click on search button
        // #Search step 2.1: Set the status, so that we will know user has clicked on search icon
        searchAppBarState.value = SearchAppBarState.TRIGGERED
    }

    fun getAllTasks() {
        _allTasks.value = RequestState.Loading
        /*
        A ViewModelScope is defined for each ViewModel in your app. Any coroutine launched in this
        scope is automatically canceled if the ViewModel is cleared. Coroutines are useful here for
        when you have work that needs to be done only if the ViewModel is active.
         */
        try {
            viewModelScope.launch {
                repository.getAllTasks.collect {
                    _allTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }
    }

    private val _selectedTask: MutableStateFlow<ToDoTask?> = MutableStateFlow(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repository.getTask(taskId = taskId).collect { task ->
                _selectedTask.value = task
            }
        }
    }

    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.addTask(toDoTask)
        }

        /**
         * If searchbar is open and we click on fab and add a new task and
         * return back to list screen, then we need to close search bar
         */
        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    private fun updateTask() {

        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.updateTask(toDoTask)
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.deleteTask(toDoTask)
        }
    }

    private fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTasks()
        }
    }

    fun handleDatabaseAction(action: Action) {
        when (action) {
            Action.ADD -> {
                addTask()
            }
            Action.UPDATE -> {
                updateTask()
            }
            Action.DELETE -> {
                deleteTask()
            }
            Action.DELETE_ALL -> {
                deleteAllTasks()
            }
            Action.UNDO -> {
                addTask()
            }
            else -> {

            }
        }
        this.action.value = Action.NO_ACTION
    }

    fun updateTaskFields(selectedTask: ToDoTask?) {
        if (selectedTask != null) {  // When we have clicked on an existing Task
            id.value = selectedTask.id
            title.value = selectedTask.title
            description.value = selectedTask.description
            priority.value = selectedTask.priority
        } else {  // When we clicked on FAB to create a new Task
            id.value = 0
            title.value = ""
            description.value = ""
            priority.value = Priority.LOW
        }
    }

    // Set character length limit for Title
    fun updateTitle(newTitle: String) {
        if (newTitle.length < MAX_TITLE_LENGTH) {
            title.value = newTitle
        }
    }

    fun validateFields(): Boolean {
        return title.value.isNotEmpty() && description.value.isNotEmpty()
    }
}

/*
ViewModel is designed to store and manage UI-related data in a lifecycle conscious way.

Using SharedViewModel, we can communicate between fragments. If we consider two fragments,
both the fragments can access the ViewModel through their activity.


 */