package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import androidx.lifecycle.viewModelScope
import com.incetro.projecttemplate.common.navigation.AppRouter
import com.incetro.projecttemplate.presentation.base.mvvm.BaseViewModel
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DemoViewModel @AssistedInject constructor(
    private val router: AppRouter,
    private val numberFactRepository: NumberFactRepository
) : BaseViewModel<DemoFragmentEvent>() {

    private val viewState: MutableStateFlow<DemoFragmentViewState> =
        MutableStateFlow(DemoFragmentViewState())

    fun getViewState(): SharedFlow<DemoFragmentViewState> = viewState
    private val currentState: DemoFragmentViewState
        get() = viewState.value

    override fun obtainEvent(event: DemoFragmentEvent) {
        reduce(event)
    }

    private var debounceNumberFactJob: Job? = null
    private fun reduce(event: DemoFragmentEvent) {
        when (event) {
            DemoFragmentEvent.IncreaseCounter -> {
                viewState.update {
                    it.copy(counter = it.counter + 1)
                }
                fetchNumberFact()
            }

            DemoFragmentEvent.DecreaseCounter -> {
                viewState.update {
                    it.copy(counter = it.counter - 1)
                }
                fetchNumberFact()
            }
        }
    }

    private fun fetchNumberFact() {
        debounceNumberFactJob?.cancel()
        debounceNumberFactJob = viewModelScope.launch {
            delay(300)
            val fact = numberFactRepository.getNumberFact(currentState.counter).text
            withContext(Dispatchers.Main) {
                viewState.update { it.copy(numberFact = fact) }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(): DemoViewModel
    }

    override fun onBackPressed() {
        router.exit()
    }
}