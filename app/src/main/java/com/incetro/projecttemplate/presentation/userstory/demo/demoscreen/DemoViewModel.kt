package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.incetro.projecttemplate.common.navigation.AppRouter
import com.incetro.projecttemplate.presentation.base.mvvm.BaseViewModel
import com.incetro.projecttemplate.presentation.userstory.demo.demoscreen.repository.NumberFactRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class DemoViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val router: AppRouter,
    private val numberFactRepository: NumberFactRepository
) : ContainerHost<DemoFragmentViewState, CommonSideEffect>, BaseViewModel() {

    override val container: Container<DemoFragmentViewState, CommonSideEffect> =
        container(DemoFragmentViewState(), savedStateHandle)

    fun incrementCounter() = intent {
        reduce {
            state.copy(counter = state.counter + 1)
        }
        getNewFact()
    }

    fun decrementCounter() = intent {
        reduce {
            state.copy(counter = state.counter - 1)
        }
        getNewFact()
    }

    private fun getNewFact() = intent {
        postSideEffect(CommonSideEffect.ShowLoading)
        val numberFact = fetchNumberFact(state.counter)
        postSideEffect(CommonSideEffect.HideLoading)
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

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): DemoViewModel
    }

    override fun onBackPressed() {
        router.exit()
    }

    companion object {
        fun provideFactory(
            owner: SavedStateRegistryOwner,
            assistedFactory: Factory
        ): ViewModelProvider.Factory = object : AbstractSavedStateViewModelFactory(owner, null) {

            override fun <T : ViewModel> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                return assistedFactory.create(handle) as T
            }
        }
    }
}