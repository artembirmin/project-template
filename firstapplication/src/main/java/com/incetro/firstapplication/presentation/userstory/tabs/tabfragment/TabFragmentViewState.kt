/*
 * ProjectTemplate
 *
 * Created by artembirmin on 12/8/2023.
 */

package com.incetro.firstapplication.presentation.userstory.tabs.tabfragment

import com.incetro.firstapplication.presentation.base.mvvm.view.ViewState
import kotlinx.parcelize.Parcelize

@Parcelize
data class TabFragmentViewState(
    val currentTab: BottomTab = BottomTab.FIRST
) : ViewState() {
    override fun copyState(): TabFragmentViewState = this.copy()
}