/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.projecttemplate.presentation.userstory.demo.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.InstanceKeeperDispatcher
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory
import com.incetro.projecttemplate.BuildConfig
import com.incetro.projecttemplate.common.di.scope.FeatureScope
import com.incetro.projecttemplate.presentation.base.mvikotlin.BackPressedStore
import com.incetro.projecttemplate.presentation.base.mvikotlin.CommonNavigationStoreExecutor
import com.incetro.projecttemplate.presentation.base.mvikotlin.NavigationLabel
import com.incetro.projecttemplate.presentation.userstory.demo.demoscreen.NumberFactRepository
import com.incetro.projecttemplate.presentation.userstory.demo.demoscreen.NumberFactRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class DemoModule {

    @Binds
    @FeatureScope
    abstract fun provideNumberFactRepository(numberFactRepositoryImpl: NumberFactRepositoryImpl): NumberFactRepository

    companion object {
        @Provides
        @FeatureScope
        fun provideStoreFactory(): StoreFactory {
            return if (BuildConfig.DEBUG)
                LoggingStoreFactory(delegate = TimeTravelStoreFactory())
            else DefaultStoreFactory()
        }

        @Provides
        @FeatureScope
        fun provideStoreInstanceKeeper(): InstanceKeeper {
            return InstanceKeeperDispatcher()
        }

        @Provides
        @FeatureScope
        fun provideCommonNavigationStore(
            storeFactory: StoreFactory,
            commonNavigationStoreExecutor: CommonNavigationStoreExecutor
        ): BackPressedStore {
            return object : BackPressedStore(),
                Store<BackPressedStore.BackPressedIntent, Unit, NavigationLabel.Exit>
                by storeFactory.create(
                    name = BackPressedStore.NAME,
                    executorFactory = { commonNavigationStoreExecutor },
                    initialState = Unit
                ) {}
        }
    }
}