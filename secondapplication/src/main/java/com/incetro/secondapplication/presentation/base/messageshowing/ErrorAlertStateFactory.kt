/*
 * ProjectTemplate
 *
 * Created by artembirmin on 7/9/2023.
 */

package com.incetro.secondapplication.presentation.base.messageshowing

import com.google.gson.Gson
import com.incetro.secondapplication.BuildConfig
import com.incetro.secondapplication.R
import com.incetro.secondapplication.common.manager.ResourcesManager
import com.incetro.secondapplication.entity.errors.ErrorBody
import com.incetro.secondapplication.model.data.network.NetworkConnectionError
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

class ErrorAlertStateFactory {

    companion object {

        fun handleError(
            error: Throwable,
            resourcesManager: ResourcesManager,
            payload: Any? = null,
            onErrorCode: AlertDialogState.(errorCode: Int) -> AlertDialogState = { _ -> this },
        ): AlertDialogState {
            var errorCode: Int? = null
            val errorMessage: String = when (error) {
                is NetworkConnectionError,
                is UnknownHostException,
                is ConnectException,
                is SSLHandshakeException,
                is SocketTimeoutException,
                -> {
                    if (BuildConfig.DEBUG) {
                        error.message.toString()
                    } else {
                        resourcesManager.getString(R.string.error_common_text)
                    }
                }

                is HttpException -> {
                    val errorBody = error.parseHttpError()
                    val errorMessage =
                        errorBody.errors ?: resourcesManager.getString(R.string.error_common_text)
                    errorCode = errorBody.code
//                when (errorCode) {
//                    else -> errorMessage
//                }
                    errorMessage
                }

                else -> {
                    error.message ?: resourcesManager.getString(R.string.error_common_text)
                }
            }

            val state = AlertDialogState(
                title = DialogString.StringResText(R.string.error_title),
                text = DialogString.StringText(errorMessage),
                icon = R.drawable.ic_dialog_warning,
                isVisible = true
            )
            return if (errorCode != null) {
                state.onErrorCode(errorCode)
            } else {
                state
            }
        }

        fun HttpException.parseHttpError(): ErrorBody {
            val charStream = response()?.errorBody()?.charStream()
            return charStream?.let {
                Gson().fromJson(charStream, ErrorBody::class.java)
            } ?: response()?.errorBody()?.string()?.let {
                Gson().fromJson(it, ErrorBody::class.java)
            } ?: ErrorBody(null, 0, null, false)
        }
    }

}