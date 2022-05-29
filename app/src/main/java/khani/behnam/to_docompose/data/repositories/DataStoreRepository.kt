package khani.behnam.to_docompose.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import khani.behnam.to_docompose.data.models.Priority
import khani.behnam.to_docompose.util.Constants.PREFERENCE_KEY
import khani.behnam.to_docompose.util.Constants.PREFERENCE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


import javax.inject.Inject

// Declare one extension property on our context object
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

@ViewModelScoped  // We are going to inject this class into sharedViewModel
class DataStoreRepository @Inject constructor(
    // Injecting a context
    @ApplicationContext private val context: Context
) {
    private object PreferenceKeys {
        val sortKey = stringPreferencesKey(name = PREFERENCE_KEY)
    }

    // This is our data store we created at line 16 by using an Extension Function over Context
    private val dataStore = context.dataStore

    // Saving priority or sort state
    suspend fun persistSortState(priority: Priority) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.sortKey] = priority.name
        }
    }

    // Reading sort state
    // emit is used with Flow
    val readSortState: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException)
                emit(emptyPreferences())
            else {
                throw exception
            }
        }
        .map { preferences ->
            // If no priority is saved, return name of NONE
            val sortState = preferences[PreferenceKeys.sortKey] ?: Priority.NONE.name
            sortState
        }
}