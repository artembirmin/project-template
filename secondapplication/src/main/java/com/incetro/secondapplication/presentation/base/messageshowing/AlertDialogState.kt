/*
 * ProjectTemplate
 *
 * Created by artembirmin on 11/8/2023.
 */

package com.incetro.secondapplication.presentation.base.messageshowing

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.text.AnnotatedString
import com.incetro.secondapplication.R

/**
 * Mark this by [kotlinx.parcelize.IgnoredOnParcel].
 */
data class AlertDialogState(
    val isVisible: Boolean = false,
    val title: DialogString? = null,
    val text: DialogString? = null,
    @StringRes val positiveText: Int? = R.string.ok,
    @StringRes val negativeText: Int? = null,
    @DrawableRes val icon: Int? = null,
    val onPositiveClick: (() -> Unit)? = null,
    val onNegativeClick: (() -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null,
    val cancelable: Boolean = true,
)

sealed interface DialogString {
    data class StringText(val value: String) : DialogString
    data class StringResText(@StringRes val value: Int) : DialogString
    data class AnnotatedStringText(val value: AnnotatedString) : DialogString
}