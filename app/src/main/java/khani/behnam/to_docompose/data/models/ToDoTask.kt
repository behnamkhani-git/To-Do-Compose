package khani.behnam.to_docompose.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import khani.behnam.to_docompose.util.Constants

@Entity(tableName = Constants.DATABASE_TABLE)
data class ToDoTask(
    @PrimaryKey
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority
)