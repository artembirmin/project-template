/*
 * ProjectTemplate
 *
 * Created by artembirmin on 5/11/2022.
 */

package com.incetro.secondapplication.common.di.app

import android.content.Context
import com.github.terrakok.cicerone.NavigatorHolder
import com.incetro.secondapplication.app.App
import com.incetro.secondapplication.app.AppLauncher
import com.incetro.secondapplication.common.di.app.module.AppModule
import com.incetro.secondapplication.common.di.app.module.AppNavigationModule
import com.incetro.secondapplication.common.di.app.module.CommonAppModule
import com.incetro.secondapplication.common.di.app.module.DatabaseModule
import com.incetro.secondapplication.common.di.app.module.FlowNavigationModule
import com.incetro.secondapplication.common.di.app.module.NetworkModule
import com.incetro.secondapplication.common.di.app.module.TabNavigationModule
import com.incetro.secondapplication.common.di.qualifier.AppNavigation
import com.incetro.secondapplication.common.di.qualifier.FlowNavigation
import com.incetro.secondapplication.common.di.qualifier.TabNavigation
import com.incetro.secondapplication.common.manager.ResourcesManager
import com.incetro.secondapplication.common.navigation.AppRouter
import com.incetro.secondapplication.common.navigation.FlowRouter
import com.incetro.secondapplication.common.navigation.TabRouter
import com.incetro.secondapplication.model.data.database.AppDatabase
import com.incetro.secondapplication.model.data.database.demo.DemoDao
import com.incetro.secondapplication.model.data.network.api.DemoApi
import com.incetro.secondapplication.presentation.base.mvvm.viewmodel.BaseViewModelDependencies
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