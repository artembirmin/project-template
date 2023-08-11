/*
 * ProjectTemplate
 *
 * Created by artembirmin on 5/11/2022.
 */

@file:Suppress("FunctionName")

package com.incetro.projecttemplate.common.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.incetro.projecttemplate.presentation.userstory.demo.demoscreen.DemoFragment

/**
 * App screens for navigation with Cicerone.
 */
object Screens {
    fun DemoScreen(): FragmentScreen =
        FragmentScreen() {
            DemoFragment.newInstance()
        }
}