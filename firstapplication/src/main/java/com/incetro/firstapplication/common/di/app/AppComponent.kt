/*
 * ProjectTemplate
 *
 * Created by artembirmin on 5/11/2022.
 */

package com.incetro.firstapplication.common.di.app

import android.content.Context
import com.github.terrakok.cicerone.NavigatorHolder
import com.incetro.firstapplication.app.App
import com.incetro.firstapplication.app.AppLauncher
import com.incetro.firstapplication.common.di.app.module.AppModule
import com.incetro.firstapplication.common.di.app.module.AppNavigationModule
import com.incetro.firstapplication.common.di.app.module.CommonAppModule
import com.incetro.firstapplication.common.di.app.module.DatabaseModule
import com.incetro.firstapplication.common.di.app.module.FlowNavigationModule
import com.incetro.firstapplication.common.di.app.module.NetworkModule
import com.incetro.firstapplication.common.di.app.module.TabNavigationModule
import com.incetro.firstapplication.common.di.qualifier.AppNavigation
import com.incetro.firstapplication.common.di.qualifier.FlowNavigation
import com.incetro.firstapplication.common.di.qualifier.TabNavigation
import com.incetro.firstapplication.common.manager.ResourcesManager
import com.incetro.firstapplication.common.navigation.AppRouter
import com.incetro.firstapplication.common.navigation.FlowRouter
import com.incetro.firstapplication.common.navigation.TabRouter
import com.incetro.firstapplication.model.data.database.AppDatabase
import com.incetro.firstapplication.model.data.database.demo.DemoDao
import com.incetro.firstapplication.model.data.network.api.DemoApi
import com.incetro.firstapplication.presentation.base.mvvm.viewmodel.BaseViewModelDependencies
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        CommonAppModule::class,
        AppNavigationModule::class,
        FlowNavigationModule::class,
        TabNavigationModule::class,
        DatabaseModule::class,
        NetworkModule::class,
    ]
)
interface AppComponent {

    fun inject(app: App)

    // AppModule
    fun provideContext(): Context
    fun provideAppLauncher(): AppLauncher
    fun provideDeps(): BaseViewModelDependencies

    // CommonAppModule

    // AppNavigationModule
    @AppNavigation
    fun provideAppNavigationHolder(): NavigatorHolder
    fun provideAppRouter(): AppRouter

    // FlowNavigationModule
    @FlowNavigation
    fun provideFlowNavigationHolder(): NavigatorHolder
    fun provideFlowRouter(): FlowRouter

    // TabNavigationModule
    @TabNavigation
    fun provideTabNavigationHolder(): NavigatorHolder
    fun provideTabRouter(): TabRouter

    // Database module
    fun provideAppDatabase(): AppDatabase
    fun provideDemoDao(): DemoDao

    // Network module
    fun provideDemoApi(): DemoApi

    // Other
    fun provideResourcesManager(): ResourcesManager

    @Component.Builder
    interface Builder {
        fun appModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }
}