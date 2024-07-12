/*
 * ProjectTemplate
 *
 * Created by artembirmin on 9/11/2023.
 */

package com.incetro.secondapplication.presentation.userstory.demofragment.demoscreen

import com.incetro.secondapplication.presentation.base.messageshowing.AlertDialogState
import com.incetro.secondapplication.presentation.base.mvvm.view.LoaderState
import com.incetro.secondapplication.presentation.base.mvvm.view.ViewState
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class DemoFragmentViewState(
    @IgnoredOnParcel override var dialogState: AlertDialogState = AlertDialogState(),
    @IgnoredOnParcel override var loaderState: LoaderState = LoaderState(),
) : ViewState() {
    override fun copyState(): DemoFragmentViewState = this.copy()
}