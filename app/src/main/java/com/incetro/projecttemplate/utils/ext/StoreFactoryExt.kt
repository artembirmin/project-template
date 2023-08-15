/*
 * todomvikotlin
 *
 * Created by artembirmin on 17/1/2023.
 */

package com.incetro.projecttemplate.utils.ext

import com.arkivanov.mvikotlin.core.store.Bootstrapper
import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.incetro.projecttemplate.presentation.base.mvikotlin.CommonAction

fun <Intent : Any, Message : Any, State : Any, Label : Any> StoreFactory.createStoreSimple(
    name: String? = null,
    initialState: State,
    executor: Executor<Intent, CommonAction, State, Message, Label>,
    reducer: Reducer<State, Message>,
    autoInit: Boolean = false,
    bootstrapper: Bootstrapper<CommonAction>? = SimpleBootstrapper(CommonAction.Init)
): Store<Intent, State, Label> {
    return this.createStore(
        name = name,
        autoInit = autoInit,
        initialState = initialState,
        bootstrapper = bootstrapper,
        executor = executor,
        reducer = reducer
    )
}

fun <Intent : Any, Action : Any, Message : Any, State : Any, Label : Any> StoreFactory.createStore(
    name: String? = null,
    initialState: State,
    executor: Executor<Intent, Action, State, Message, Label>,
    reducer: Reducer<State, Message>,
    autoInit: Boolean = false,
    bootstrapper: Bootstrapper<Action>? = null
): Store<Intent, State, Label> {
    return this.create(
        name = name,
        autoInit = autoInit,
        initialState = initialState,
        bootstrapper = bootstrapper,
        executorFactory = { executor },
        reducer = reducer
    )
}
