/*
 * ProjectTemplate
 *
 * Created by artembirmin on 5/11/2022.
 */

@file:Suppress("FunctionName")

package com.incetro.projecttemplate.common.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.incetro.projecttemplate.presentation.userstory.start.StartFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.demoflow.FlowFragmentInsideTab
import com.incetro.projecttemplate.presentation.userstory.tabs.demoflow.SeparateFlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.demoflow.Tab1FlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.demoflow.Tab2FlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.demoflow.Tab3FlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.demoflow.Tab4FlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.demoscreen.DemoFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.demoscreen.DemoFragmentViewState
import com.incetro.projecttemplate.presentation.userstory.tabs.tabfragment.TabNavigationFragment

/**
 * App screens for navigation with Cicerone.
 */
object Screens {
    fun DemoScreen(initState: DemoFragmentViewState): FragmentScreen =
        FragmentScreen() {
            DemoFragment.newInstance(initState)
        }

    fun TabNavigationScreen(): FragmentScreen =
        FragmentScreen() {
            TabNavigationFragment.newInstance()
        }

    fun StartScreen(): FragmentScreen =
        FragmentScreen() {
            StartFragment.newInstance()
        }

    fun Tab1FlowScreen(): FragmentScreen =
        FragmentScreen() {
            Tab1FlowFragment.newInstance()
        }

    fun Tab2FlowScreen(): FragmentScreen =
        FragmentScreen() {
            Tab2FlowFragment.newInstance()
        }

    fun Tab3FlowScreen(): FragmentScreen =
        FragmentScreen() {
            Tab3FlowFragment.newInstance()
        }

    fun Tab4FlowScreen(): FragmentScreen =
        FragmentScreen() {
            Tab4FlowFragment.newInstance()
        }

    var flowNumber = 1

    fun FlowInsideTabScreen(isInsideTab: Boolean = true): FragmentScreen =
        FragmentScreen("Tab5FlowScreen${flowNumber++}") {
            FlowFragmentInsideTab.newInstance(isInsideTab)
        }

    fun SeparateFlowScreen(): FragmentScreen =
        FragmentScreen("Tab5FlowScreen${flowNumber++}") {
            SeparateFlowFragment.newInstance()
        }


//    fun DemoFlowScreen(initParams: DemoFlowFragmentState): FragmentScreen =
//        FragmentScreen("DemoFlowScreen") {
//            DemoFlowFragment.newInstance(initParams)
//        }
}