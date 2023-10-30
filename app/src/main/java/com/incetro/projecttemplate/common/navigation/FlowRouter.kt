/*
 * ProjectTemplate
 *
 * Created by artembirmin on 24/10/2023.
 */

package com.incetro.projecttemplate.common.navigation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen


class FlowRouter(private val mainRouter: Router) : Router() {

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
