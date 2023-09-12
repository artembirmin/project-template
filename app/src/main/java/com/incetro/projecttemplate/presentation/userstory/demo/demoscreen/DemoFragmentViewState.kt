/*
 * ProjectTemplate
 *
 * Created by artembirmin on 12/8/2023.
 */

package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import com.incetro.projecttemplate.presentation.base.messageshowing.AlertDialogState
import com.incetro.projecttemplate.presentation.base.mvvm.view.LoaderState
import com.incetro.projecttemplate.presentation.base.mvvm.view.ViewState
import com.incetro.projecttemplate.ui.Theme
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class DemoFragmentViewState(
    val counter: Int = 0,
    val numberFact: String = "",
//    val newTheme: Theme = Theme.DARK,
    val screenNo: Int = 0,
    @IgnoredOnParcel override var dialogState: AlertDialogState = AlertDialogState(),
    @IgnoredOnParcel override var loaderState: LoaderState = LoaderState()
) : ViewState() {
    override fun copyState(): DemoFragmentViewState = this.copy()
}