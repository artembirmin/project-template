/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.projecttemplate.presentation.userstory.demo.di

import com.incetro.projecttemplate.common.di.scope.FeatureScope
import com.incetro.projecttemplate.presentation.userstory.demo.demoscreen.repository.NumberFactRepository
import com.incetro.projecttemplate.presentation.userstory.demo.demoscreen.repository.NumberFactRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class DemoModule {

    @Binds
    @FeatureScope
    abstract fun provideNumberFactRepository(numberFactRepositoryImpl: NumberFactRepositoryImpl): NumberFactRepository
}