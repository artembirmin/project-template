/*
 * ProjectTemplate
 *
 * Created by artembirmin on 31/10/2023.
 */

package com.incetro.secondapplication.common.di.app.module

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.incetro.secondapplication.common.di.qualifier.TabNavigation
import com.incetro.secondapplication.common.navigation.AppRouter
import com.incetro.secondapplication.common.navigation.TabRouter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TabNavigationModule {

    @Provides
    @Singleton
    fun provideFlowCicerone(tabNavigationRouter: AppRouter): Cicerone<TabRouter> =
        Cicerone.create(TabRouter(tabNavigationRouter))

    @Provides
    @Singleton
    fun provideTabRouter(cicerone: Cicerone<TabRouter>): TabRouter =
        cicerone.router

    @Provides
    @Singleton
    @TabNavigation
    fun provideTabNavigationHolder(cicerone: Cicerone<TabRouter>): NavigatorHolder =
        cicerone.getNavigatorHolder()

}