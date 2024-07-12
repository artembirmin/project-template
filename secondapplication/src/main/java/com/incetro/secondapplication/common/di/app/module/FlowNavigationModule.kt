/*
 * ProjectTemplate
 *
 * Created by artembirmin on 30/10/2023.
 */

package com.incetro.secondapplication.common.di.app.module

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.incetro.secondapplication.common.di.qualifier.FlowNavigation
import com.incetro.secondapplication.common.navigation.AppRouter
import com.incetro.secondapplication.common.navigation.FlowRouter
import com.incetro.secondapplication.common.navigation.TabRouter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FlowNavigationModule {

    @Provides
    @Singleton
    fun provideFlowCicerone(
        appRouter: AppRouter,
        tabNavigationRouter: TabRouter,
    ): Cicerone<FlowRouter> =
        Cicerone.create(FlowRouter(appRouter, tabNavigationRouter))

    @Provides
    @Singleton
    fun provideFlowRouter(cicerone: Cicerone<FlowRouter>): FlowRouter =
        cicerone.router

    @Provides
    @Singleton
    @FlowNavigation
    fun provideFlowNavigationHolder(cicerone: Cicerone<FlowRouter>): NavigatorHolder =
        cicerone.getNavigatorHolder()
}