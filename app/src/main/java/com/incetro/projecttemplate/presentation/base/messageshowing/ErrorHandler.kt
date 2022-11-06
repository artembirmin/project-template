/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.projecttemplate.presentation.base.messageshowing

import android.app.AlertDialog
import android.content.Context
import com.incetro.projecttemplate.R
import com.incetro.projecttemplate.common.navigation.AppRouter
import com.incetro.projecttemplate.entity.errors.AppError
import com.incetro.projecttemplate.utils.ext.toReadableLogDateTime
import org.joda.time.DateTime
import timber.log.Timber
import javax.inject.Inject


class ErrorHandler @Inject constructor(
    private val appRouter: AppRouter
) {
    fun showError(appError: AppError, context: Context) {
        val error = appError.error
        showError(error, appError.payload, context)
    }

    fun showError(error: Throwable, context: Context) {
        showError(error, null, context)
    }

    private fun showError(error: Throwable, payload: Any? = null, context: Context) {
        Timber.e(error)
        showDebugError(error, context)
    }

    private fun showDebugError(
        error: Throwable,
        context: Context
    ) {
        val exceptionErrorMessage = error.message.toString()
        showDebugMessageByDialog(
            context,
            exceptionErrorMessage
        )
    }

    private fun showDebugMessageByDialog(
        context: Context,
        message: CharSequence = "",
        errorDate: String = DateTime.now().toReadableLogDateTime()
    ) {
        AlertDialog.Builder(context)
            .setTitle(errorDate)
            .setMessage(message)
            .setPositiveButton(R.string.alert_button_ok) { _, _ -> }
            .create().show()
    }
}

