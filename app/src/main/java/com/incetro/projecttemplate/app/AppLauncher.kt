/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.projecttemplate.app

import com.incetro.projecttemplate.common.navigation.AppRouter
import com.incetro.projecttemplate.common.navigation.Screens

class AppLauncher(
    private val router: AppRouter,
) {
    /**
     *  Initialized and launches application.
     */
    fun start() {
        router.newRootScreen(Screens.DemoScreen())
    }
}