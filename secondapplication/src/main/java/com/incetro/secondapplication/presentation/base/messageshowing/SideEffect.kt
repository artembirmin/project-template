/*
 * ProjectTemplate
 *
 * Created by artembirmin on 4/9/2023.
 */

package com.incetro.secondapplication.presentation.base.messageshowing

import androidx.annotation.DrawableRes

sealed interface SideEffect {

    data class ToastMessageState(
        val text: String = "",
        @DrawableRes val icon: Int? = null,
    ) : SideEffect

    object None : SideEffect
}