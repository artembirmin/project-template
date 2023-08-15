/*
 * todomvikotlin
 *
 * Created by artembirmin on 28/1/2023.
 */

package com.incetro.projecttemplate.presentation.base.mvikotlin

import com.github.terrakok.cicerone.Screen

sealed class NavigationLabel : CommonLabel() {
    data class NavigateTo(val screen: Screen) : NavigationLabel()
    data class NewRootScreen(val screen: Screen) : NavigationLabel()
    data class ReplaceScreen(val screen: Screen) : NavigationLabel()
    data class BackTo(val screen: Screen) : NavigationLabel()
    object Exit : NavigationLabel()
}