/*
 * ProjectTemplate
 *
 * Created by artembirmin on 2/11/2023.
 */

package com.incetro.projecttemplate.presentation.userstory.tabs.flows

import com.github.terrakok.cicerone.Screen
import com.incetro.projecttemplate.common.navigation.Screens
import com.incetro.projecttemplate.presentation.base.mvvm.view.TabFlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.di.DemoComponent

class Tab1FlowFragment : TabFlowFragment() {

    override var launchScreen: Screen = Screens.FlowInsideTabScreen()

    override fun inject() = DemoComponent.Manager.getComponent().inject(this)
    override fun release() = DemoComponent.Manager.releaseComponent()

    companion object {
        fun newInstance(): Tab1FlowFragment = Tab1FlowFragment()
    }
}
