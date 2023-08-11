package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import com.incetro.projecttemplate.common.navigation.AppRouter
import com.incetro.projecttemplate.presentation.base.mvvm.BaseViewModel
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class MvvmViewModel @AssistedInject constructor(
    private val router: AppRouter
) : BaseViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(): MvvmViewModel
    }

    override fun onBackPressed() {
        router.exit()
    }
}