/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.projecttemplate.presentation.base.messageshowing

import androidx.annotation.StringRes
import com.incetro.projecttemplate.R
import com.incetro.projecttemplate.entity.errors.AppError
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface MessageView {

    fun showError(error: AppError)

    fun showError(error: Throwable)

    fun showMessageByAlertDialog(
        @StringRes title: Int? = null,
        @StringRes message: Int? = null,
        @StringRes positiveText: Int = R.string.alert_button_ok,
        @StringRes negativeText: Int? = null,
        onPositiveButtonClick: (() -> Unit)? = null,
        onNegativeButtonClick: (() -> Unit)? = null,
        onDismiss: (() -> Unit)? = null
    )
}