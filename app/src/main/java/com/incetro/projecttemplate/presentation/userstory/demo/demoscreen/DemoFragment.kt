package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.incetro.projecttemplate.R
import com.incetro.projecttemplate.databinding.FragmentDemoBinding
import com.incetro.projecttemplate.presentation.base.mvikotlin.BaseStoreFragment
import com.incetro.projecttemplate.presentation.base.mvikotlin.CommonLabel
import com.incetro.projecttemplate.presentation.userstory.demo.di.DemoComponent
import com.incetro.projecttemplate.utils.ext.createStoreSimple
import com.incetro.projecttemplate.utils.ext.lazyViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class DemoFragment : BaseStoreFragment<FragmentDemoBinding>() {

    override val layoutRes = R.layout.fragment_demo

    override lateinit var store: DemoStore
    override val storeName = DemoStore.NAME

    override fun inject() = DemoComponent.Manager.getComponent().inject(this)

    override fun release() = Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val viewState: DemoStore.State by store.states
                    .collectAsStateWithLifecycle(DemoStore.State())
                MaterialTheme() {
                    Counter(viewState)
                }
            }
        }
    }

    @Inject
    fun createStore(
        storeFactory: StoreFactory,
        reducer: DemoStoreReducer,
        executor: DemoStoreExecutor
    ) {
        Timber.d("CREATE STORE")
        store = storeInstanceKeeper.getStore(key = storeName) {
            object : DemoStore(),
                Store<DemoStore.Intent, DemoStore.State, CommonLabel>
                by storeFactory.createStoreSimple(
                    name = storeName,
                    initialState = State(),
                    reducer = reducer,
                    executor = executor
                ) {}
                .also {
                    stateKeeper.register(key = storeName) {
                        it.state
                    }
                }
        }
    }

    @androidx.compose.ui.tooling.preview.Preview(
        backgroundColor = 0xFFFFFFFF,
        device = "spec:width=1080px,height=1920px,dpi=440"
    )
    @Composable
    fun Counter(
        viewState: DemoStore.State = DemoStore.State(
            counter = 12,
            numberFact = "qqqqkqqqqqqqqqqqqqqqqq2wqqqqqq"
        )
    ) {
        Surface(modifier = Modifier.padding(16.dp)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Card(
                    elevation = 8.dp,
                    modifier = Modifier.padding(bottom = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    backgroundColor = Color.Magenta,
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .defaultMinSize(minWidth = 120.dp),
                        text = viewState.counter.toString(),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                Row() {
                    Button(
                        onClick = { store.accept(DemoStore.Intent.DecreaseCounter) },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(text = "-")
                    }
                    Button(
                        onClick = {store.accept(DemoStore.Intent.IncreaseCounter)  },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(text = "+")
                    }
                }
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = viewState.numberFact,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    @Composable
    fun <T> Flow<T>.collectAsStateWithLifecycle(
        initialValue: T,
        lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
        minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
        context: CoroutineContext = EmptyCoroutineContext
    ): State<T> = collectAsStateWithLifecycle(
        initialValue = initialValue,
        lifecycle = lifecycleOwner.lifecycle,
        minActiveState = minActiveState,
        context = context
    )

    @Composable
    fun <T> Flow<T>.collectAsStateWithLifecycle(
        initialValue: T,
        lifecycle: Lifecycle,
        minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
        context: CoroutineContext = EmptyCoroutineContext
    ): State<T> {
        return produceState(initialValue, this, lifecycle, minActiveState, context) {
            lifecycle.repeatOnLifecycle(minActiveState) {
                if (context == EmptyCoroutineContext) {
                    this@collectAsStateWithLifecycle.collect { this@produceState.value = it }
                } else withContext(context) {
                    this@collectAsStateWithLifecycle.collect { this@produceState.value = it }
                }
            }
        }
    }

    companion object {
        fun newInstance() = DemoFragment()
    }
}