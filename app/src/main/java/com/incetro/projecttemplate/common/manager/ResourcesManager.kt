/*
 * ProjectTemplate
 *
 * Created by artembirmin on 5/11/2022.
 */

package com.incetro.projecttemplate.common.manager

import android.content.Context
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourcesManager @Inject constructor(private val context: Context) {

    fun getString(@StringRes stringRes: Int): String {
        return context.getString(stringRes)
    }

    fun getString(@StringRes stringRes: Int, vararg formatArgs: Any?): String {
        return context.getString(stringRes, *formatArgs)
    }

    fun getJsonFromFile(@RawRes fileRes: Int): String {
        return context.resources.openRawResource(fileRes)
            .bufferedReader().use { it.readText() }
    }
}
