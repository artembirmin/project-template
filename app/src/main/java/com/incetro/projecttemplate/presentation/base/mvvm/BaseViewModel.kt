package com.incetro.projecttemplate.presentation.base.mvvm

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.incetro.projecttemplate.R
import com.incetro.projecttemplate.entity.errors.AppError
import com.incetro.projecttemplate.presentation.base.messageshowing.AlertDialogState
import com.incetro.projecttemplate.presentation.base.messageshowing.ToastMessageState
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable


abstract class BaseViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    fun isLoading(): LiveData<Boolean> = isLoadingLiveData
    fun showErrorEvent(): SingleLiveEvent<AppError> = showErrorLiveDataEvent
    fun showDialog(): SingleLiveEvent<AlertDialogState> = showDialogLiveDataEvent
    fun showMessage(): SingleLiveEvent<ToastMessageState> = showToastMessageLiveDataEvent

    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    private val showErrorLiveDataEvent: SingleLiveEvent<AppError> = SingleLiveEvent()
    private val showDialogLiveDataEvent: SingleLiveEvent<AlertDialogState> = SingleLiveEvent()
    private val showToastMessageLiveDataEvent: SingleLiveEvent<ToastMessageState> =
        SingleLiveEvent()

    protected fun Disposable.addDisposable(): Disposable {
        compositeDisposable.add(this)
        return this
    }

    protected fun setLoading(isLoading: Boolean) {
        isLoadingLiveData.value = isLoading
    }

    protected fun showError(appError: AppError) {
        showErrorLiveDataEvent.value = appError
    }

    protected fun showError(error: Throwable) {
        showErrorLiveDataEvent.value = AppError(error)
    }

    fun showDialog(
        title: String = "",
        body: String = "",
        @StringRes positiveText: Int = R.string.ok,
        @StringRes negativeText: Int = 0,
        @DrawableRes icon: Int = 0,
        onPositiveClick: (() -> Unit)? = null,
        onNegativeClick: (() -> Unit)? = null,
        onDismiss: (() -> Unit)? = null,
        cancelable: Boolean = true
    ) {
        val dialogParams = AlertDialogState(
            true,
            title,
            body,
            positiveText,
            negativeText,
            icon,
            onPositiveClick,
            onNegativeClick,
            onDismiss,
            cancelable
        )
        showDialog(dialogParams)
    }

    protected fun showDialog(dialogParams: AlertDialogState) {
        showDialogLiveDataEvent.value = dialogParams
    }

    protected fun showMessage(messageParams: ToastMessageState) {
        showToastMessageLiveDataEvent.value = messageParams
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    open fun onBackPressed() {}

}