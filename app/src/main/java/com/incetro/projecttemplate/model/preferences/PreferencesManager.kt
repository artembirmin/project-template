package com.incetro.projecttemplate.model.preferences

import android.content.Context
import com.google.gson.Gson
import com.incetro.projecttemplate.ui.theme.Theme
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(
    private val context: Context,
    private val gson: Gson
) {

    private fun getSharedPreferences(prefsName: String) =
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    fun clearSessionPrefs() {
        authPrefs.edit().clear().apply()
        appPrefs.edit().clear().apply()
    }

    //region auth
    private val AUTH_DATA = "auth_data"
    private val KEY_TOKEN = "user_token"
    private val KEY_REFRESH_TOKEN = "refresh_token"

    private val authPrefs by lazy { getSharedPreferences(AUTH_DATA) }

    var token: String?
        get() = authPrefs.getString(KEY_TOKEN, "")
        set(value) {
            authPrefs.edit().putString(KEY_TOKEN, value).apply()
        }

    var refreshToken: String?
        get() = authPrefs.getString(KEY_REFRESH_TOKEN, null)
        set(value) {
            appPrefs.edit().putString(KEY_REFRESH_TOKEN, value).apply()
        }


    //endregion

    //region global
    private val GLOBAL_DATA = "GLOBAL_DATA"
    private val KEY_THEME_ID = "app_theme_id"

    private val globalPrefs by lazy { getSharedPreferences(GLOBAL_DATA) }

    var appTheme: Theme
        get() {
            val defaultThemeName = Theme.SYSTEM.name
            val themeName =
                globalPrefs.getString(KEY_THEME_ID, defaultThemeName) ?: defaultThemeName
            return Theme.valueOf(themeName)
        }
        set(value) {
            globalPrefs.edit().putString(KEY_THEME_ID, value.name).apply()
        }

    //endregion

    //region app
    private val APP_DATA = "app_data"

    private val appPrefs by lazy { getSharedPreferences(APP_DATA) }


}