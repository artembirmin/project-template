/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.secondapplication.app

import com.incetro.secondapplication.common.navigation.AppRouter
import com.incetro.secondapplication.common.navigation.Screens
import com.incetro.secondapplication.model.data.preferences.PreferencesManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppLauncher @Inject constructor(
    private val router: AppRouter,
    private val preferencesManager: PreferencesManager,
) {
    /**
     *  Initialized and launches application.
     */
    fun start() {
        App.theme.value = preferencesManager.appTheme

        router.newRootScreen(Screens.TabNavigationScreen())
    }
}