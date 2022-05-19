package khani.behnam.to_docompose.data.repositories

import khani.behnam.to_docompose.data.ToDoDao
import khani.behnam.to_docompose.data.models.ToDoTask
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToDoRepository @Inject constructor(private val toDoDao: ToDoDao) {
    val getAllTasks: Flow<List<ToDoTask>> = toDoDao.getAllTasks()
    suspend fun sortByLowPriority(): Flow<List<ToDoTask>> = toDoDao.sortByLowPriority()
    suspend fun softByHighPriority(): Flow<List<ToDoTask>> = toDoDao.sortByHighPriority()

    fun getTask(taskId: Int): Flow<ToDoTask> = toDoDao.getTask(taskId)

    suspend fun addTask(toDoTask: ToDoTask) = toDoDao.addTask(toDoTask)

    suspend fun updateTask(toDoTask: ToDoTask) = toDoDao.updateTask(toDoTask)

    suspend fun deleteTask(toDoTask: ToDoTask) = toDoDao.deleteTask(toDoTask)

    suspend fun deleteAllTasks() = toDoDao.deleteAllTasks()
}