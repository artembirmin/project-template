/*
 * todomvikotlin
 *
 * Created by artembirmin on 27/1/2023.
 */

package com.incetro.projecttemplate.presentation.base.mvikotlin

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.incetro.projecttemplate.common.di.scope.FeatureScope
import javax.inject.Inject

abstract class BackPressedStore :
    Store<BackPressedStore.BackPressedIntent, Unit, NavigationLabel.Exit> {

    sealed class BackPressedIntent {
        object OnBackPressed : BackPressedIntent()
    }

    companion object {
        val NAME = BackPressedStore::class.simpleName!!
    }
}

@FeatureScope
class CommonNavigationStoreExecutor @Inject constructor() :
    CoroutineExecutor<BackPressedStore.BackPressedIntent, Unit, Unit, Unit, NavigationLabel.Exit>() {

    override fun executeIntent(intent: BackPressedStore.BackPressedIntent, getState: () -> Unit) {

        when (intent) {
            BackPressedStore.BackPressedIntent.OnBackPressed -> publish(NavigationLabel.Exit)
        }
    }
}