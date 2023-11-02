package com.incetro.projecttemplate.presentation.userstory.start

import androidx.lifecycle.SavedStateHandle
import com.incetro.projecttemplate.common.navigation.AppRouter
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

class StartViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val router: AppRouter,
    baseViewModelDependencies: BaseViewModelDependencies
) : BaseViewModel<StartFragmentViewState, SideEffect>(baseViewModelDependencies) {

    override val container: Container<StartFragmentViewState, SideEffect> =
        container(
            initialState = savedStateHandle.get<StartFragmentViewState>(INITIAL_STATE_KEY)
                ?: StartFragmentViewState(),
            savedStateHandle = savedStateHandle,
            buildSettings = {
                exceptionHandler = coroutineExceptionHandler
            },
            onCreate = { }
        )

    fun onNavigateToTabsClick() {
        intent {
            router.navigateTo(Screens.TabNavigationScreen())
        }
    }

    fun onReplaceOnTabsClick() {
        intent {
            router.newRootScreen(Screens.TabNavigationScreen())
        }
    }

    fun onExitClick() {
        intent {
            router.exit()
        }
    }

    override fun onBackPressed() {
        router.exit()
    }

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<StartViewModel> {
        override fun create(handle: SavedStateHandle): StartViewModel
    }
}