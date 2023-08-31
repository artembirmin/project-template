/*
 * ProjectTemplate
 *
 * Created by artembirmin on 29/8/2023.
 */

package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.incetro.projecttemplate.R

sealed interface CommonSideEffect {

    data class Toast(
        val message: String,
        @DrawableRes val icon: Int? = null
    ) : CommonSideEffect

    data class Dialog(
        val title: CharSequence = "",
        val body: CharSequence = "",
        @StringRes val positiveText: Int = R.string.ok,
        @StringRes val negativeText: Int = 0,
        @DrawableRes val icon: Int = 0,
        val onPositiveClick: (() -> Unit)? = null,
        val onNegativeClick: (() -> Unit)? = null,
        val onDismiss: (() -> Unit)? = null,
        val cancelable: Boolean = true
    ) : CommonSideEffect

    data class ErrorDialog(val error: Throwable) : CommonSideEffect

    object ShowLoading : CommonSideEffect

    object HideLoading : CommonSideEffect

    object None : CommonSideEffect
}