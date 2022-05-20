package khani.behnam.to_docompose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import khani.behnam.to_docompose.data.models.ToDoTask
import khani.behnam.to_docompose.data.repositories.ToDoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
To enable injection of a ViewModel by Hilt use the @HiltViewModel annotation
 */
@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: ToDoRepository)
    : ViewModel() {
    /*
    Flow vs LiveData:
    Flows are based on Coroutines by default.
    Also you can use powerful operators like map and filter in flows
    As they use coroutines, so flows are more testable.

    Flows are hot and cold.
    StateFlow is hot. it means it keep emitting values even if there are no collectors
    A cold flow means it would not emit anything if there is no collector.
     */
    private val _allTasks = MutableStateFlow<List<ToDoTask>>(emptyList() /*default value is an empty list */)
    /* This is publicly exposed for our composable */
    val allTasks: StateFlow<List<ToDoTask>> = _allTasks

    fun getAllTasks(){
        /*
        A ViewModelScope is defined for each ViewModel in your app. Any coroutine launched in this
        scope is automatically canceled if the ViewModel is cleared. Coroutines are useful here for
        when you have work that needs to be done only if the ViewModel is active.
         */
        viewModelScope.launch {
            repository.getAllTasks.collect {
                _allTasks.value = it
            }
        }
    }
}

/*
ViewModel is designed to store and manage UI-related data in a lifecycle conscious way.

Using SharedViewModel, we can communicate between fragments. If we consider two fragments,
both the fragments can access the ViewModel through their activity.


 */