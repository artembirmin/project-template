/*
 * ProjectTemplate
 *
 * Created by artembirmin on 4/9/2023.
 */

package com.incetro.projecttemplate.presentation.base.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.incetro.projecttemplate.entity.errors.AppError
import com.incetro.projecttemplate.presentation.base.messageshowing.AlertDialogState
import com.incetro.projecttemplate.presentation.base.messageshowing.ErrorHandler
import com.incetro.projecttemplate.presentation.base.messageshowing.SideEffect
import com.incetro.projecttemplate.presentation.base.mvvm.view.SingleLiveEvent
import com.incetro.projecttemplate.presentation.base.mvvm.view.ViewState
import com.incetro.projecttemplate.presentation.base.mvvm.view.updateDialog
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

abstract class BaseViewModel<S : ViewState, E : SideEffect> : ViewModel(),
    ContainerHost<S, E> {

    protected val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var errorHandler: ErrorHandler

    protected val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, error ->
            // TODO Implement error handler
            intent {
                reduce {
                    state.updateDialog {
                        AlertDialogState(
                            isVisible = true,
                            title = "Error",
                            text = error.message.toString(),
                            onDismiss = { onDismissDialog() })
                    }
                }
            }
        }

    fun onDismissDialog() = intent {
        reduce {
            state.updateDialog {
                AlertDialogState()
            }
        }
    }

    fun isLoading(): LiveData<Boolean> = isLoadingLiveData
    fun showErrorEvent(): SingleLiveEvent<AppError> = showErrorLiveDataEvent
    fun showMessage(): SingleLiveEvent<SideEffect.ToastMessageState> = showToastMessageLiveDataEvent

    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    private val showErrorLiveDataEvent: SingleLiveEvent<AppError> = SingleLiveEvent()
    private val showToastMessageLiveDataEvent: SingleLiveEvent<SideEffect.ToastMessageState> =
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

    protected fun showMessage(messageParams: SideEffect.ToastMessageState) {
        showToastMessageLiveDataEvent.value = messageParams
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    open fun onBackPressed() {}

}