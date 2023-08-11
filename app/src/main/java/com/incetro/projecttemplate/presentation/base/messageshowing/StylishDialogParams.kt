/*
 * ProjectTemplate
 *
 * Created by artembirmin on 11/8/2023.
 */

package com.incetro.projecttemplate.presentation.base.messageshowing

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.incetro.projecttemplate.R

data class StylishDialogParams(
    val title: CharSequence = "",
    val body: CharSequence = "",
    @StringRes val positiveText: Int = R.string.ok,
    @StringRes val negativeText: Int = 0,
    @DrawableRes val icon: Int = 0,
    val onPositiveClick: (() -> Unit)? = null,
    val onNegativeClick: (() -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null,
    val cancelable: Boolean = true
)