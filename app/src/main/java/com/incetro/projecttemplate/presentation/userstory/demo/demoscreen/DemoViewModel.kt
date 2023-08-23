package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import androidx.lifecycle.viewModelScope
import com.incetro.projecttemplate.common.navigation.AppRouter
import com.incetro.projecttemplate.presentation.base.mvvm.BaseViewModel
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx3.await
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class DemoViewModel @AssistedInject constructor(
    private val router: AppRouter,
    private val numberFactRepository: NumberFactRepository
) : ContainerHost<DemoFragmentViewState, Nothing>, BaseViewModel() {

    override val container: Container<DemoFragmentViewState, Nothing> =
        container(DemoFragmentViewState())

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
        val numberFact = fetchNumberFact(state.counter)
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
        fun create(): DemoViewModel
    }

    override fun onBackPressed() {
        router.exit()
    }
}