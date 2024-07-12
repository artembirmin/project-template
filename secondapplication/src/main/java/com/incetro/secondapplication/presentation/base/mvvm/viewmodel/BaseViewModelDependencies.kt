/*
 * ProjectTemplate
 *
 * Created by artembirmin on 7/9/2023.
 */

package com.incetro.secondapplication.presentation.base.mvvm.viewmodel

import com.incetro.secondapplication.common.manager.ResourcesManager
import com.incetro.secondapplication.common.navigation.AppRouter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseViewModelDependencies @Inject constructor(
    val appRouter: AppRouter,
    val resourcesManager: ResourcesManager,
)