package com.incetro.secondapplication.presentation.userstory.tabs.tabfragment

import androidx.lifecycle.SavedStateHandle
import com.incetro.secondapplication.presentation.base.messageshowing.SideEffect
import com.incetro.secondapplication.presentation.base.mvvm.viewmodel.BaseViewModel
import com.incetro.secondapplication.presentation.base.mvvm.viewmodel.BaseViewModelDependencies
import com.incetro.secondapplication.presentation.base.mvvm.viewmodel.INITIAL_STATE_KEY
import com.incetro.secondapplication.presentation.base.mvvm.viewmodel.ViewModelAssistedFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class TabFragmentViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    baseViewModelDependencies: BaseViewModelDependencies,
) : BaseViewModel<TabFragmentViewState, SideEffect>(baseViewModelDependencies) {

    override val container: Container<TabFragmentViewState, SideEffect> =
        container(
            initialState = savedStateHandle.get<TabFragmentViewState>(INITIAL_STATE_KEY)
                ?: TabFragmentViewState(),
            savedStateHandle = savedStateHandle,
            buildSettings = {
                exceptionHandler = coroutineExceptionHandler
            },
            onCreate = { }
        )

    override fun onBackPressed() {

    }

    fun onSelectTab(tab: BottomTab) {
        intent {
            reduce {
                state.copy(currentTab = tab)
            }
        }
    }

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<TabFragmentViewModel> {
        override fun create(handle: SavedStateHandle): TabFragmentViewModel
    }
}