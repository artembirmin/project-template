/*
 * ProjectTemplate
 *
 * Created by artembirmin on 5/11/2022.
 */

@file:Suppress("FunctionName")

package com.incetro.projecttemplate.common.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.incetro.projecttemplate.presentation.userstory.demofragment.demoscreen.DemoFragment
import com.incetro.projecttemplate.presentation.userstory.demofragment.demoscreen.DemoFragmentViewState
import com.incetro.projecttemplate.presentation.userstory.tabs.flows.Tab1FlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.flows.Tab2FlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.flows.Tab3FlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.flows.Tab4FlowFragment
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
}