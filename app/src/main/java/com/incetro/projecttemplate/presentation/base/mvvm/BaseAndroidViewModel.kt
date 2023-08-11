package com.incetro.projecttemplate.presentation.base.mvvm

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context


abstract class BaseAndroidViewModel<Event>(
    @SuppressLint("StaticFieldLeak") private val app: Application
) : BaseViewModel<Event>(),
    BaseViewModelBinding {

    /**
     * Return the application.
     */
    open fun <T : Application?> getApplication(): T {
        return app as T
    }

    fun getAppContext(): Context = getApplication()
}