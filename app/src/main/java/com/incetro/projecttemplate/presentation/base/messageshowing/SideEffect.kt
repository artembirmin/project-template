/*
 * ProjectTemplate
 *
 * Created by artembirmin on 4/9/2023.
 */

package com.incetro.projecttemplate.presentation.base.messageshowing

sealed interface SideEffect {

    data class ErrorDialog(val error: Throwable) : SideEffect

    object ShowLoading : SideEffect

    object HideLoading : SideEffect

    object None : SideEffect
}