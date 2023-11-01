/*
 * ProjectTemplate
 *
 * Created by artembirmin on 27/10/2023.
 */

package com.incetro.projecttemplate.presentation.userstory.tabs.demoflow

import com.github.terrakok.cicerone.Screen
import com.incetro.projecttemplate.common.navigation.Screens
import com.incetro.projecttemplate.presentation.base.mvvm.view.FlowFragment
import com.incetro.projecttemplate.presentation.base.mvvm.view.TabFlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.demoscreen.DemoFragmentViewState
import com.incetro.projecttemplate.presentation.userstory.tabs.di.DemoComponent

class Tab1FlowFragment : TabFlowFragment() {

    override var launchScreen: Screen = Screens.Flow1Screen()

    override fun inject() = DemoComponent.Manager.getComponent().inject(this)
    override fun release() = DemoComponent.Manager.releaseComponent()

    companion object {
        fun newInstance(): Tab1FlowFragment = Tab1FlowFragment()
    }
}

class Tab2FlowFragment : TabFlowFragment() {

    override var launchScreen: Screen = Screens.Flow1Screen()

    override fun inject() = DemoComponent.Manager.getComponent().inject(this)
    override fun release() = DemoComponent.Manager.releaseComponent()

    companion object {
        fun newInstance(): Tab2FlowFragment = Tab2FlowFragment()
    }
}

class Tab3FlowFragment : TabFlowFragment() {

    override var launchScreen: Screen = Screens.Flow1Screen()

    override fun inject() = DemoComponent.Manager.getComponent().inject(this)
    override fun release() = DemoComponent.Manager.releaseComponent()

    companion object {
        fun newInstance(): Tab3FlowFragment = Tab3FlowFragment()
    }
}

class Tab4FlowFragment : TabFlowFragment() {

    override var launchScreen: Screen = Screens.Flow1Screen()

    override fun inject() = DemoComponent.Manager.getComponent().inject(this)
    override fun release() = DemoComponent.Manager.releaseComponent()

    companion object {
        fun newInstance(): Tab4FlowFragment = Tab4FlowFragment()
    }
}

class FlowFragment1 : FlowFragment() {

    override var launchScreen: Screen = Screens.DemoScreen(DemoFragmentViewState(1))

    override fun inject() = DemoComponent.Manager.getComponent().inject(this)
    override fun release() = DemoComponent.Manager.releaseComponent()

    companion object {
        fun newInstance(): FlowFragment1 = FlowFragment1()
    }
}