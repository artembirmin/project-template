/*
 * ProjectTemplate
 *
 * Created by artembirmin on 12/8/2023.
 */

package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import com.incetro.projecttemplate.presentation.base.messageshowing.AlertDialogState
import com.incetro.projecttemplate.presentation.base.mvvm.view.ViewState
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class DemoFragmentViewState(
    val counter: Int = 0,
    val numberFact: String = "",
    @IgnoredOnParcel override var dialog: AlertDialogState = AlertDialogState(),
    @IgnoredOnParcel override var hasLoader: Boolean = false
) : ViewState() {
    override fun copyState(): DemoFragmentViewState = this.copy()
}