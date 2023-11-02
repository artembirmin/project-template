/*
 * ProjectTemplate
 *
 * Created by artembirmin on 12/8/2023.
 */

package com.incetro.projecttemplate.presentation.userstory.start

import com.incetro.projecttemplate.presentation.base.messageshowing.AlertDialogState
import com.incetro.projecttemplate.presentation.base.mvvm.view.LoaderState
import com.incetro.projecttemplate.presentation.base.mvvm.view.ViewState
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class StartFragmentViewState(
    @IgnoredOnParcel override var dialogState: AlertDialogState = AlertDialogState(),
    @IgnoredOnParcel override var loaderState: LoaderState = LoaderState()
) : ViewState() {
    override fun copyState(): StartFragmentViewState = this.copy()
}