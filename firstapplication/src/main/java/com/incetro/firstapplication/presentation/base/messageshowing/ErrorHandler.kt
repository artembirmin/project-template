/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.firstapplication.presentation.base.messageshowing

import android.app.AlertDialog
import android.content.Context
import com.incetro.firstapplication.R
import com.incetro.firstapplication.common.navigation.AppRouter
import com.incetro.firstapplication.entity.errors.AppError
import com.incetro.firstapplication.utils.ext.toReadableLogDateTime
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
            .setPositiveButton(R.string.ok) { _, _ -> }
            .create().show()
    }
}

