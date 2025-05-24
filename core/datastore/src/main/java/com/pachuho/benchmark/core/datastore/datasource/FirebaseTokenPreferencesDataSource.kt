package com.pachuho.benchmark.core.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class FirebaseTokenPreferencesDataSource @Inject constructor(
    @Named("firebaseToken") private val dataStore: DataStore<Preferences>,
) {
    object PreferencesKey {
        val FIREBASE_TOKEN = stringPreferencesKey("firebase_token")
    }

    val firebaseToken = dataStore.data.map { preferences ->
        preferences[PreferencesKey.FIREBASE_TOKEN]
    }

    suspend fun updateFirebaseToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.FIREBASE_TOKEN] = token
        }
    }

    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKey.FIREBASE_TOKEN)
        }
    }
}
