package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.incetro.projecttemplate.common.navigation.AppRouter
import com.incetro.projecttemplate.presentation.base.mvvm.BaseViewModel
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class DemoViewModel @AssistedInject constructor(
    private val router: AppRouter
) : BaseViewModel<DemoFragmentEvent>() {

    private val currentViewState: MutableLiveData<DemoFragmentViewState> =
        MutableLiveData(DemoFragmentViewState())
    val viewState: LiveData<DemoFragmentViewState> = currentViewState

    override fun obtainEvent(event: DemoFragmentEvent) {
        currentViewState.value?.let { reduce(event, it) }
    }

    private fun reduce(event: DemoFragmentEvent, currentState: DemoFragmentViewState) {
        when (event) {

            else -> {}
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