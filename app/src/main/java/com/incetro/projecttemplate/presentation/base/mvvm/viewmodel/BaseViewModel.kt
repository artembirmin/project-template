/*
 * ProjectTemplate
 *
 * Created by artembirmin on 4/9/2023.
 */

package com.incetro.projecttemplate.presentation.base.mvvm.viewmodel

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.incetro.projecttemplate.R
import com.incetro.projecttemplate.entity.errors.AppError
import com.incetro.projecttemplate.presentation.base.messageshowing.AlertDialogState
import com.incetro.projecttemplate.presentation.base.messageshowing.ToastMessageState
import com.incetro.projecttemplate.presentation.base.messageshowing.SideEffect
import com.incetro.projecttemplate.presentation.base.mvvm.view.SingleLiveEvent
import com.incetro.projecttemplate.presentation.base.mvvm.view.ViewState
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.ContainerHost
import timber.log.Timber


abstract class BaseViewModel<S : ViewState, E : SideEffect> : ViewModel(),
    ContainerHost<S, E> {

    protected val compositeDisposable = CompositeDisposable()

    protected val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, error ->
            Timber.e(error)
        }

    fun isLoading(): LiveData<Boolean> = isLoadingLiveData
    fun showErrorEvent(): SingleLiveEvent<AppError> = showErrorLiveDataEvent
    fun showMessage(): SingleLiveEvent<ToastMessageState> = showToastMessageLiveDataEvent

    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    private val showErrorLiveDataEvent: SingleLiveEvent<AppError> = SingleLiveEvent()
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

    protected fun showMessage(messageParams: ToastMessageState) {
        showToastMessageLiveDataEvent.value = messageParams
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    open fun onBackPressed() {}

}