/*
 * ProjectTemplate
 *
 * Created by artembirmin on 15/8/2023.
 */

package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen


import android.os.Parcelable
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.utils.JvmSerializable
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.incetro.projecttemplate.presentation.base.mvikotlin.CommonAction
import com.incetro.projecttemplate.presentation.base.mvikotlin.CommonLabel
import com.incetro.projecttemplate.presentation.userstory.demo.demoscreen.DemoStore.Message
import com.incetro.projecttemplate.presentation.userstory.demo.demoscreen.DemoStore.State
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize
import timber.log.Timber
import javax.inject.Inject

abstract class DemoStore : Store<DemoStore.Intent, DemoStore.State, CommonLabel> {
    companion object {
        val NAME = DemoStore::class.simpleName!!
    }

    sealed class Intent : JvmSerializable {
        object IncreaseCounter : Intent()
        object DecreaseCounter : Intent()
    }

    sealed class Message : JvmSerializable {
        data class NumberFact(val fact: String) : Message()
        object IncreaseCounter : Message()
        object DecreaseCounter : Message()
    }

    @Parcelize
    data class State(
        val counter: Int = 0,
        val numberFact: String = ""
    ) : Parcelable, JvmSerializable
}

class DemoStoreExecutor @AssistedInject constructor(
    private val numberFactRepository: NumberFactRepository,
    @Assisted var firstNumber: Int
) : CoroutineExecutor<DemoStore.Intent, CommonAction, State, Message, CommonLabel>() {

    @AssistedFactory
    interface Factory {
        fun create(firstNumber: Int): DemoStoreExecutor
    }

    override fun executeAction(action: CommonAction, getState: () -> State) {
       fetchNumberFact(firstNumber)
    }

    override fun executeIntent(intent: DemoStore.Intent, getState: () -> State) {
        when (intent) {
            DemoStore.Intent.IncreaseCounter -> {
                dispatch(Message.IncreaseCounter)
                fetchNumberFact(getState().counter)
            }

            DemoStore.Intent.DecreaseCounter -> {
                dispatch(Message.DecreaseCounter)
                fetchNumberFact(getState().counter)
            }
        }
    }

    private var debounceNumberFactJob: Job? = null
    private fun fetchNumberFact(number: Int) {
        debounceNumberFactJob?.cancel()
        debounceNumberFactJob = scope.launch {
            delay(300)
            val fact = numberFactRepository.getNumberFact(number).text
            withContext(Dispatchers.Main) {
                dispatch(Message.NumberFact(fact))
            }
        }
    }
}

class DemoStoreReducer @Inject constructor() : Reducer<State, Message> {
    override fun DemoStore.State.reduce(msg: Message): DemoStore.State =
        when (msg) {
            is Message.NumberFact -> copy(numberFact = msg.fact)
            Message.IncreaseCounter -> copy(counter = counter + 1)
            Message.DecreaseCounter -> copy(counter = counter - 1)
        }
}