/*
 * ProjectTemplate
 *
 * Created by artembirmin on 5/11/2022.
 */

package com.incetro.projecttemplate.common.di.app

import android.content.Context
import com.github.terrakok.cicerone.NavigatorHolder
import com.incetro.projecttemplate.app.App
import com.incetro.projecttemplate.app.AppLauncher
import com.incetro.projecttemplate.common.di.app.module.AppModule
import com.incetro.projecttemplate.common.di.app.module.AppNavigationModule
import com.incetro.projecttemplate.common.di.app.module.CommonAppModule
import com.incetro.projecttemplate.common.manager.ResourcesManager
import com.incetro.projecttemplate.common.navigation.AppRouter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        CommonAppModule::class,
        AppNavigationModule::class,
    ]
)
interface AppComponent {

    fun inject(app: App)

    // AppModule
    fun provideContext(): Context
    fun provideAppLauncher(): AppLauncher

    // CommonAppModule

    // AppNavigationModule
    fun provideNavigationHolder(): NavigatorHolder
    fun provideAppRouter(): AppRouter

    // Other
    fun provideResourcesManager(): ResourcesManager

    @Component.Builder
    interface Builder {
        fun appModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }
}