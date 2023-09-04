package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.incetro.projecttemplate.common.navigation.AppRouter
import com.incetro.projecttemplate.presentation.base.messageshowing.AlertDialogState
import com.incetro.projecttemplate.presentation.base.messageshowing.SideEffect
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.BaseViewModel
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.DEFAULT_STATE_KEY
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.ViewModelAssistedFactory
import com.incetro.projecttemplate.presentation.userstory.demo.demoscreen.repository.NumberFactRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber

class DemoViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val router: AppRouter,
    private val numberFactRepository: NumberFactRepository
) : BaseViewModel<DemoFragmentViewState, SideEffect>() {

    override val container: Container<DemoFragmentViewState, SideEffect> =
        container(
            initialState = savedStateHandle.get<DemoFragmentViewState>(DEFAULT_STATE_KEY)
                ?: DemoFragmentViewState(),
            savedStateHandle = savedStateHandle,
            buildSettings = {
                exceptionHandler = coroutineExceptionHandler
            },
            onCreate = {
                getNewFact()
            }
        )

    // DELETEME
    override fun onCleared() {
        container.sideEffectFlow
        Timber.e("onCleared")
        super.onCleared()
    }

    fun incrementCounter() = intent {
        reduce {
            val counter = state.counter + 1
            Timber.e("counter = $counter")


            state.copy(counter = counter)
        }
        if (state.counter == 10) {
            Timber.e("if counter = 10 true")
            val alertDialogState = AlertDialogState(
                isVisible = true,
                title = "1234",
                text = "dkdkdkdke",
                onPositiveClick = {
                    reset()
                })
            postSideEffect(alertDialogState)

//            delay(1000)
//
//            val dialog = AlertDialogState(
//                isVisible = true,
//                title = "234565432",
//                text = "dkdkdkdke",
//                onPositiveClick = {
//                    reset()
//                })
//
//            postSideEffect(dialog)
        }

        getNewFact()
    }

    fun reset() = intent {
        Timber.e("RESET")
        reduce {
            state.copy(counter = 0, numberFact = "")
        }
    }

    fun decrementCounter() = intent {
        reduce {
            state.copy(counter = state.counter - 1)
        }
        getNewFact()
    }

    private fun getNewFact() = intent {
//        postSideEffect(SideEffect.ShowLoading)
        val numberFact = fetchNumberFact(state.counter)
//        postSideEffect(SideEffect.HideLoading)
        reduce {
            state.copy(numberFact = numberFact)
        }
    }

    private var debounceNumberFactJob: Job? = null
    private suspend fun fetchNumberFact(number: Int): String {
        var fact: String = container.stateFlow.value.numberFact
        debounceNumberFactJob?.cancel()
        debounceNumberFactJob = viewModelScope.launch {
            delay(300)
            fact = numberFactRepository.getNumberFact(number).text
        }
        debounceNumberFactJob?.join()
        return fact
    }

    override fun onBackPressed() {
        router.exit()
    }

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<DemoViewModel> {
        override fun create(handle: SavedStateHandle): DemoViewModel
    }
}