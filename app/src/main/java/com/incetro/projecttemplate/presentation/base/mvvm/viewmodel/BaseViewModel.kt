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
import com.incetro.projecttemplate.presentation.base.messageshowing.ErrorAlertStateFactory
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


abstract class BaseViewModel<S : ViewState, E : SideEffect>(
    private val dependencies: BaseViewModelDependencies
) : ViewModel(), ContainerHost<S, E> {

    protected val compositeDisposable = CompositeDisposable()

    protected val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, error ->
            intent {
                reduce {
                    val errorAlertState =
                        ErrorAlertStateFactory.handleError(error, dependencies.resourcesManager)
                    state.updateDialog {
                        errorAlertState.copy(onDismiss = { onDismissDialog() })
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

    protected fun Disposable.addDisposable(): Disposable {
        compositeDisposable.add(this)
        return this
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    open fun onBackPressed() {}

}