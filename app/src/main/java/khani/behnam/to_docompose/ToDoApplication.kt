package khani.behnam.to_docompose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/*
You always need to do that. Because Hilt needs to set up a lot of classes behind the scenes for us
and for that Hilt also needs to know where our application class is. It needs to have access to the
application context in case to create one of our dependencies. So in general, Hilts needs to know
where the application class is.
 */
@HiltAndroidApp
class ToDoApplication: Application()