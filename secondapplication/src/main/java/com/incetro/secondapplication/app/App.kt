/*
 * ProjectTemplate
 *
 * Created by artembirmin on 3/11/2022.
 */

package com.incetro.secondapplication.app

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import com.incetro.secondapplication.BuildConfig
import com.incetro.secondapplication.common.di.componentmanager.ComponentsManager
import com.incetro.secondapplication.presentation.ui.theme.Theme
import com.incetro.secondapplication.utils.FileLoggingTree
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        inject()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.plant(FileLoggingTree(this))
        }
    }

    private fun inject() {
        ComponentsManager.init(this)
        ComponentsManager.getInstance().applicationComponent.inject(this)
    }

    companion object {
        val theme = mutableStateOf(Theme.SYSTEM)
    }
}