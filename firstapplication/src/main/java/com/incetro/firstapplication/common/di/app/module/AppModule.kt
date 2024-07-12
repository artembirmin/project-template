/*
 * ProjectTemplate
 *
 * Created by artembirmin on 4/11/2022.
 */

package com.incetro.firstapplication.common.di.app.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return application.applicationContext
    }
}