/*
 * ProjectTemplate
 *
 * Created by artembirmin on 31/10/2023.
 */

package com.incetro.firstapplication.common.navigation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen

/**
 * Router for navigation with Cicerone.
 */
class TabRouter(private val mainRouter: AppRouter) : Router() {

    fun startFlow(screen: Screen) {
        mainRouter.navigateTo(screen)
    }

    fun newRootFlow(screen: Screen) {
        mainRouter.newRootScreen(screen)
    }

    fun finishFlow() {
        mainRouter.exit()
    }

    fun exitTwice() {
        exit()
        exit()
    }
}