/*
 * ProjectTemplate
 *
 * Created by artembirmin on 24/10/2023.
 */

package com.incetro.projecttemplate.common.navigation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen


class FlowRouter(private val appRouter: AppRouter, private val tabRouter: Router) : Router() {

    fun startFlow(screen: Screen) {
        appRouter.navigateTo(screen)
    }

    fun newRootFlow(screen: Screen) {
        appRouter.newRootScreen(screen)
    }

    fun finishFlow() {
        appRouter.exit()
    }

    fun startFlowInsideTab(screen: Screen) {
        tabRouter.navigateTo(screen)
    }

    fun newRootFlowInsideTab(screen: Screen) {
        tabRouter.newRootScreen(screen)
    }

    fun finishFlowInsideTab() {
        tabRouter.exit()
    }

    fun exitTwice() {
        exit()
        exit()
    }
}
