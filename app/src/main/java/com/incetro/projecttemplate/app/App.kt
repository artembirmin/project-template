/*
 * ProjectTemplate
 *
 * Created by artembirmin on 3/11/2022.
 */

package com.incetro.projecttemplate.app

import android.app.Application
import com.incetro.projecttemplate.BuildConfig
import com.incetro.projecttemplate.common.di.componentmanager.ComponentsManager
import com.incetro.projecttemplate.utils.FileLoggingTree
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        inject()
        Timber.tag("TAG").e("BuildConfig.DEBUG= " + BuildConfig.DEBUG)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.plant(FileLoggingTree(this))
        }
    }

    private fun inject() {
        ComponentsManager.init(this)
        ComponentsManager.getInstance().applicationComponent.inject(this)
    }
}