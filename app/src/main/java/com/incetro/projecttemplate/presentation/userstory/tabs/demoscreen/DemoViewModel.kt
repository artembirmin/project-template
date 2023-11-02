package com.incetro.projecttemplate.presentation.userstory.tabs.demoscreen

import androidx.lifecycle.SavedStateHandle
import com.incetro.projecttemplate.R
import com.incetro.projecttemplate.common.navigation.FlowRouter
import com.incetro.projecttemplate.common.navigation.Screens
import com.incetro.projecttemplate.presentation.base.messageshowing.DialogString
import com.incetro.projecttemplate.presentation.base.messageshowing.SideEffect
import com.incetro.projecttemplate.presentation.base.mvvm.view.updateDialog
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.BaseViewModel
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.BaseViewModelDependencies
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.INITIAL_STATE_KEY
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.ViewModelAssistedFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

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
        intent {
            if (state.screenNumber == 1 && state.isHomeTab && state.isFirstFlowFragmenInTab) {
                reduce {
                    state.updateDialog { dialogState ->
                        dialogState.copy(
                            isVisible = true,
                            title = DialogString.StringText("Do you really wont to exit?"),
                            positiveText = R.string.exit,
                            negativeText = R.string.cancel,
                            onPositiveClick = { router.exit() },
                            onDismiss = {
                                intent {
                                    reduce {
                                        state.updateDialog { it.copy(isVisible = false) }
                                    }
                                }
                            }
                        )
                    }
                }
            } else {
                router.exit()
            }
        }
    }

    fun onNavigateClick() {
        intent {
            router.navigateTo(Screens.DemoScreen(DemoFragmentViewState(screenNumber = state.screenNumber + 1)))
        }
    }

    fun onNavigateFlowInsideTab() {
        intent {
            router.startFlowInsideTab(Screens.FlowInsideTabScreen())
        }
    }

    fun onNavigateSeparateFlow() {
        intent {
            router.startFlow(Screens.FlowInsideTabScreen(isInsideTab = false))
        }
    }

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<DemoViewModel> {
        override fun create(handle: SavedStateHandle): DemoViewModel
    }
}