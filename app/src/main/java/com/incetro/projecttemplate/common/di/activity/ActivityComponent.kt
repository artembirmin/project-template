/*
 * ProjectTemplate
 *
 * Created by artembirmin on 5/11/2022.
 */

package com.incetro.projecttemplate.common.di.activity

import android.content.Context
import com.github.terrakok.cicerone.NavigatorHolder
import com.incetro.projecttemplate.app.AppActivity
import com.incetro.projecttemplate.common.di.app.AppComponent
import com.incetro.projecttemplate.common.di.componentmanager.ComponentManager
import com.incetro.projecttemplate.common.di.componentmanager.ComponentsManager
import com.incetro.projecttemplate.common.di.qualifier.AppNavigation
import com.incetro.projecttemplate.common.di.qualifier.FlowNavigation
import com.incetro.projecttemplate.common.di.qualifier.TabNavigation
import com.incetro.projecttemplate.common.di.scope.ActivityScope
import com.incetro.projecttemplate.common.manager.ResourcesManager
import com.incetro.projecttemplate.common.navigation.AppRouter
import com.incetro.projecttemplate.common.navigation.FlowRouter
import com.incetro.projecttemplate.common.navigation.TabRouter
import com.incetro.projecttemplate.model.data.database.AppDatabase
import com.incetro.projecttemplate.model.data.database.demo.DemoDao
import com.incetro.projecttemplate.model.data.network.api.DemoApi
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.BaseViewModelDependencies
import com.incetro.projecttemplate.presentation.userstory.tabs.flows.Tab1FlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.flows.Tab2FlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.flows.Tab3FlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.flows.Tab4FlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.tabfragment.TabNavigationFragment
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