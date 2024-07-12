/*
 * ProjectTemplate
 *
 * Created by artembirmin on 2/11/2023.
 */

package com.incetro.firstapplication.presentation.userstory.tabs.flows

import com.github.terrakok.cicerone.Screen
import com.incetro.firstapplication.common.di.activity.ActivityComponent
import com.incetro.firstapplication.common.navigation.Screens
import com.incetro.firstapplication.presentation.base.mvvm.view.TabFlowFragment
import com.incetro.firstapplication.presentation.userstory.demofragment.demoscreen.DemoFragmentViewState

class Tab4FlowFragment : TabFlowFragment() {

    override var launchScreen: Screen = Screens.DemoScreen(DemoFragmentViewState())

    override fun inject() = ActivityComponent.Manager.getComponent().inject(this)
    override fun release() = ActivityComponent.Manager.releaseComponent()

    companion object {
        fun newInstance(): Tab4FlowFragment = Tab4FlowFragment()
    }
}
