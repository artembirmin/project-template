/*
 * ProjectTemplate
 *
 * Created by artembirmin on 11/8/2023.
 */

package com.incetro.projecttemplate.presentation.base.messageshowing

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.incetro.projecttemplate.R

/**
 * Mark this by [kotlinx.parcelize.IgnoredOnParcel].
 */
data class AlertDialogState(
    val isVisible: Boolean = false,
    /**
     * If you want to use SpannableString, you should use [androidx.compose.ui.text.AnnotatedString].
     */
    val title: String = "",
    val text: String = "",
    @StringRes val positiveText: Int? = R.string.ok,
    @StringRes val negativeText: Int? = null,
    @DrawableRes val icon: Int? = null,
    val onPositiveClick: (() -> Unit)? = null,
    val onNegativeClick: (() -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null,
    val cancelable: Boolean = true
): SideEffect