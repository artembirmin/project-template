/*
 * ProjectTemplate
 *
 * Created by artembirmin on 27/10/2023.
 */

package com.incetro.projecttemplate.presentation.userstory.tabs.flows

import com.github.terrakok.cicerone.Screen
import com.incetro.projecttemplate.common.navigation.Screens
import com.incetro.projecttemplate.presentation.base.mvvm.view.FlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.demoscreen.DemoFragmentViewState
import com.incetro.projecttemplate.presentation.userstory.tabs.di.DemoComponent

class FlowFragmentImpl(override val isFlowInsideTab: Boolean) : FlowFragment() {

    override var launchScreen: Screen = Screens.DemoScreen(DemoFragmentViewState(1))

    override fun inject() = DemoComponent.Manager.getComponent().inject(this)
    override fun release() = DemoComponent.Manager.releaseComponent()

    companion object {
        fun newInstance(isFlowInsideTab: Boolean = false): FlowFragmentImpl =
            FlowFragmentImpl(isFlowInsideTab)
    }
}