/*
 * ProjectTemplate
 *
 * Created by artembirmin on 2/11/2023.
 */

package com.incetro.projecttemplate.presentation.userstory.tabs.flows

import com.github.terrakok.cicerone.Screen
import com.incetro.projecttemplate.common.di.activity.ActivityComponent
import com.incetro.projecttemplate.common.navigation.Screens
import com.incetro.projecttemplate.presentation.base.mvvm.view.TabFlowFragment
import com.incetro.projecttemplate.presentation.userstory.demofragment.demoscreen.DemoFragmentViewState

class Tab2FlowFragment : TabFlowFragment() {

    override var launchScreen: Screen = Screens.DemoScreen(DemoFragmentViewState())

    override fun inject() = ActivityComponent.Manager.getComponent().inject(this)
    override fun release() = ActivityComponent.Manager.releaseComponent()

    companion object {
        fun newInstance(): Tab2FlowFragment = Tab2FlowFragment()
    }
}
