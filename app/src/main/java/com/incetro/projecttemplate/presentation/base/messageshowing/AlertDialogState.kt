/*
 * ProjectTemplate
 *
 * Created by artembirmin on 11/8/2023.
 */

package com.incetro.projecttemplate.presentation.base.messageshowing

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.incetro.projecttemplate.R
import kotlinx.parcelize.Parcelize

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
    @StringRes val negativeText: Int? = 0,
    @DrawableRes val icon: Int? = 0,
    val onPositiveClick: (() -> Unit)? = null,
    val onNegativeClick: (() -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null,
    val cancelable: Boolean = true
)