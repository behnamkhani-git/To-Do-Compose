package khani.behnam.to_docompose.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import khani.behnam.to_docompose.data.ToDoDatabase
import khani.behnam.to_docompose.util.Constants
import javax.inject.Singleton

/*
When using Dagger Hilt you need to declare one or multiple modules. These modules are like containers
for dependencies that lives for specific time.
 for example, here we define dependencies that lives as long as our application does
Here, usually we have Retrofit instance, Room instance ...

By Dagger Hilt we can scope our dependencies. So we cannot only have an app module with singleton
dependencies,  we can also have for example a main activity module (a module that contains
dependencies that only live as long as our main activity does and when we switch the activity,
we can't inject those main activity dependencies anymore in another activity.

 */
@Module /* tell the Dagger Hilt that hey this is a module */
/* tell the Dagger Hilt how to scope this module
We can choose also ApplicationComponent(That is renamed to SingletonComponent) or ActivityComponent
or FragmentComponent or ViewComponent
*/
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /*
    Inside the module, we need to give Dagger Hilt somehow a blueprint how it can construct
    the dependencies that we want to inject (By declaring functions)
    The name convention for naming these functions is to start everything with provide
     */
    @Singleton /* makes a function singleton, so we have only one instance
     We use it for Room, Retrofit and ...
     */
    @Provides /* Hey Dagger, we want to provide a dependency which is a database in this case */
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ToDoDatabase::class.java,
        Constants.DATABASE_NAME
    )
        .build()

    @Singleton
    @Provides
    fun provideDao(database: ToDoDatabase) = database.toDoDao()
}