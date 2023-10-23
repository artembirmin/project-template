/*
 * ProjectTemplate
 *
 * Created by artembirmin on 4/9/2023.
 */

package com.incetro.projecttemplate.presentation.base.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import com.incetro.projecttemplate.presentation.base.messageshowing.AlertDialogState
import com.incetro.projecttemplate.presentation.base.messageshowing.ErrorAlertStateFactory
import com.incetro.projecttemplate.presentation.base.messageshowing.SideEffect
import com.incetro.projecttemplate.presentation.base.mvvm.view.LoaderState
import com.incetro.projecttemplate.presentation.base.mvvm.view.ViewState
import com.incetro.projecttemplate.presentation.base.mvvm.view.updateDialog
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import timber.log.Timber


abstract class BaseViewModel<S : ViewState, E : SideEffect>(
    private val dependencies: BaseViewModelDependencies
) : ViewModel(), ContainerHost<S, E> {

    protected val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, error ->
            Timber.e(error)
            intent {
                reduce {
                    val errorAlertState =
                        ErrorAlertStateFactory.handleError(error, dependencies.resourcesManager)
                    state.updateDialog {
                        errorAlertState.copy(onDismiss = { onDismissDialog() })
                    }.apply {
                        loaderState = LoaderState(hasLoading = false)
                    }
                }
            }
        }

    protected fun onDismissDialog() = intent {
        reduce {
            state.updateDialog {
                AlertDialogState()
            }
        }
    }

    open fun onBackPressed() {}

}