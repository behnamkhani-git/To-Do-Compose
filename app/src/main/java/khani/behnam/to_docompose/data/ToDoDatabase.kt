package khani.behnam.to_docompose.data

import androidx.room.Database
import khani.behnam.to_docompose.data.models.ToDoTask

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase {
    abstract fun toDoDao(): ToDoDao
}