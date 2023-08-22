/*
 * ProjectTemplate
 *
 * Created by artembirmin on 16/8/2023.
 */

package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.statekeeper.StateKeeper
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.incetro.projecttemplate.presentation.base.mvikotlin.CommonLabel
import com.incetro.projecttemplate.utils.ext.createStoreSimple
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject


class DemoStoreFactory @AssistedInject constructor(
    @Assisted var firstNumber: Int,
    @Assisted var stateKeeper: StateKeeper,
    private val instanceKeeper: InstanceKeeper,
    private val executorFactory: DemoStoreExecutor.Factory,
    private val reducer: DemoStoreReducer,
    private val storeFactory: StoreFactory
) {

    @AssistedFactory
    interface Factory {
        fun create(firstNumber: Int, stateKeeper: StateKeeper): DemoStoreFactory
    }

    fun store(): DemoStore {
        val storeName = DemoStore.NAME
        return instanceKeeper.getStore(key = storeName) {
            object : DemoStore(),
                Store<DemoStore.Intent, DemoStore.State, CommonLabel>
                by storeFactory.createStoreSimple(
                    name = storeName,
                    initialState = State(counter = firstNumber),
                    reducer = reducer,
                    executor = executorFactory.create(firstNumber)
                ) {}
                .also {
                    stateKeeper.register(key = DemoStore.NAME) {
                        it.state
                    }
                }
        }
    }
}