package com.pachuho.benchmark.core.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.pachuho.benchmark.core.model.AuthToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.flow.map

class AuthTokenPreferencesDataSource @Inject constructor(
    @Named("authToken") private val dataStore: DataStore<Preferences>,
) {
    object PreferencesKey {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    val authTokenFlow: Flow<AuthToken?> = dataStore.data
        .map { preferences ->
            val accessToken = preferences[PreferencesKey.ACCESS_TOKEN]
            val refreshToken = preferences[PreferencesKey.REFRESH_TOKEN]

            if (accessToken != null && refreshToken != null) {
                AuthToken(accessToken, refreshToken)
            } else {
                null
            }
        }

    suspend fun saveAuthToken(token: AuthToken) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.ACCESS_TOKEN] = token.accessToken
            preferences[PreferencesKey.REFRESH_TOKEN] = token.refreshToken
        }
    }

    suspend fun clearAuthToken() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKey.ACCESS_TOKEN)
            preferences.remove(PreferencesKey.REFRESH_TOKEN)
        }
    }
}
