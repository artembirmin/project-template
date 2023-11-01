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
import com.incetro.projecttemplate.common.di.app.module.DatabaseModule
import com.incetro.projecttemplate.common.di.app.module.FlowNavigationModule
import com.incetro.projecttemplate.common.di.app.module.NetworkModule
import com.incetro.projecttemplate.common.di.app.module.TabNavigationModule
import com.incetro.projecttemplate.common.di.qualifier.AppNavigation
import com.incetro.projecttemplate.common.di.qualifier.FlowNavigation
import com.incetro.projecttemplate.common.di.qualifier.TabNavigation
import com.incetro.projecttemplate.common.manager.ResourcesManager
import com.incetro.projecttemplate.common.navigation.AppRouter
import com.incetro.projecttemplate.common.navigation.FlowRouter
import com.incetro.projecttemplate.common.navigation.TabRouter
import com.incetro.projecttemplate.model.database.AppDatabase
import com.incetro.projecttemplate.model.database.demo.DemoDao
import com.incetro.projecttemplate.model.network.api.DemoApi
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.BaseViewModelDependencies
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