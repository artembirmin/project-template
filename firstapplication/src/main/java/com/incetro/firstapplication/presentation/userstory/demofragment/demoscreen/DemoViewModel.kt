/*
 * ProjectTemplate
 *
 * Created by artembirmin on 9/11/2023.
 */

package com.incetro.firstapplication.presentation.userstory.demofragment.demoscreen

import androidx.lifecycle.SavedStateHandle
import com.incetro.firstapplication.common.navigation.FlowRouter
import com.incetro.firstapplication.presentation.base.messageshowing.SideEffect
import com.incetro.firstapplication.presentation.base.mvvm.viewmodel.BaseViewModel
import com.incetro.firstapplication.presentation.base.mvvm.viewmodel.BaseViewModelDependencies
import com.incetro.firstapplication.presentation.base.mvvm.viewmodel.INITIAL_STATE_KEY
import com.incetro.firstapplication.presentation.base.mvvm.viewmodel.ViewModelAssistedFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.orbitmvi.orbit.Container
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
        router.exit()
    }

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<DemoViewModel> {
        override fun create(handle: SavedStateHandle): DemoViewModel
    }
}