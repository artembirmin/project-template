package com.incetro.projecttemplate.presentation.userstory.tabs.demoscreen

import androidx.lifecycle.SavedStateHandle
import com.incetro.projecttemplate.common.navigation.FlowRouter
import com.incetro.projecttemplate.common.navigation.Screens
import com.incetro.projecttemplate.presentation.base.messageshowing.SideEffect
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.BaseViewModel
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.BaseViewModelDependencies
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.INITIAL_STATE_KEY
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.ViewModelAssistedFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber

class DemoViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val router: FlowRouter,
    baseViewModelDependencies: BaseViewModelDependencies
) : BaseViewModel<DemoFragmentViewState, SideEffect>(baseViewModelDependencies) {

    override val container: Container<DemoFragmentViewState, SideEffect> =
        container(
            initialState = savedStateHandle.get<DemoFragmentViewState>(INITIAL_STATE_KEY)
                ?: DemoFragmentViewState(),
            savedStateHandle = savedStateHandle,
            buildSettings = {
                exceptionHandler = coroutineExceptionHandler
            },
            onCreate = { }
        )

    override fun onBackPressed() {
        Timber.e("DemoViewModel onBackPressed")
        router.exit()
    }

    fun onNavigateClick() {
        intent {
            router.navigateTo(Screens.DemoScreen(DemoFragmentViewState(screenNumber = state.screenNumber + 1)))
        }
    }

    fun onNavigateFlow() {
        intent {
            router.startFlow(Screens.Tab5FlowScreen())
        }
    }

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<DemoViewModel> {
        override fun create(handle: SavedStateHandle): DemoViewModel
    }
}