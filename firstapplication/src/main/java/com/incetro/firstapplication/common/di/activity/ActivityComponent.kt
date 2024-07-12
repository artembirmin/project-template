/*
 * ProjectTemplate
 *
 * Created by artembirmin on 5/11/2022.
 */

package com.incetro.firstapplication.common.di.activity

import android.content.Context
import com.github.terrakok.cicerone.NavigatorHolder
import com.incetro.firstapplication.app.AppActivity
import com.incetro.firstapplication.common.di.app.AppComponent
import com.incetro.firstapplication.common.di.componentmanager.ComponentManager
import com.incetro.firstapplication.common.di.componentmanager.ComponentsManager
import com.incetro.firstapplication.common.di.qualifier.AppNavigation
import com.incetro.firstapplication.common.di.qualifier.FlowNavigation
import com.incetro.firstapplication.common.di.qualifier.TabNavigation
import com.incetro.firstapplication.common.di.scope.ActivityScope
import com.incetro.firstapplication.common.manager.ResourcesManager
import com.incetro.firstapplication.common.navigation.AppRouter
import com.incetro.firstapplication.common.navigation.FlowRouter
import com.incetro.firstapplication.common.navigation.TabRouter
import com.incetro.firstapplication.model.data.database.AppDatabase
import com.incetro.firstapplication.model.data.database.demo.DemoDao
import com.incetro.firstapplication.model.data.network.api.DemoApi
import com.incetro.firstapplication.presentation.base.mvvm.viewmodel.BaseViewModelDependencies
import com.incetro.firstapplication.presentation.userstory.tabs.flows.Tab1FlowFragment
import com.incetro.firstapplication.presentation.userstory.tabs.flows.Tab2FlowFragment
import com.incetro.firstapplication.presentation.userstory.tabs.flows.Tab3FlowFragment
import com.incetro.firstapplication.presentation.userstory.tabs.flows.Tab4FlowFragment
import com.incetro.firstapplication.presentation.userstory.tabs.tabfragment.TabNavigationFragment
import dagger.Component

@ActivityScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        ActivityModule::class
    ]
)
interface ActivityComponent {
    fun inject(appActivity: AppActivity)
    fun inject(tabNavigationFragment: TabNavigationFragment)
    fun inject(tab1FlowFragment: Tab1FlowFragment)
    fun inject(tab2FlowFragment: Tab2FlowFragment)
    fun inject(tab3FlowFragment: Tab3FlowFragment)
    fun inject(tab4FlowFragment: Tab4FlowFragment)

    // AppModule
    fun provideContext(): Context
    fun provideDeps(): BaseViewModelDependencies

    // AppNavigationModule from AppComponent
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
        fun appComponent(component: AppComponent): Builder
        fun activityModule(activityModule: ActivityModule): Builder
        fun build(): ActivityComponent
    }

    object Manager : ComponentManager<ActivityComponent>(
        clazz = ActivityComponent::class.java,
        createAndSave = {
            val componentManager = ComponentsManager.getInstance()
            val activityComponent = DaggerActivityComponent.builder()
                .appComponent(componentManager.applicationComponent)
                .activityModule(ActivityModule())
                .build()

            componentManager.addComponent(activityComponent)
        })
}