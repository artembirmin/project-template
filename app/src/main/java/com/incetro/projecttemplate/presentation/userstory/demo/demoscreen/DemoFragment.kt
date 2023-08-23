package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.incetro.projecttemplate.presentation.base.mvvm.BaseMVVMFragment
import com.incetro.projecttemplate.presentation.base.mvvm.BaseViewModel
import com.incetro.projecttemplate.presentation.userstory.demo.di.DemoComponent
import com.incetro.projecttemplate.utils.ext.lazyViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.orbitmvi.orbit.compose.collectAsState
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class DemoFragment : BaseMVVMFragment() {

    @Inject
    lateinit var viewModelFactory: DemoViewModel.Factory

    private val viewModel: DemoViewModel by lazyViewModel {
        viewModelFactory.create()
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun inject() = DemoComponent.Manager.getComponent().inject(this)

    override fun release() = Unit

    @Composable
    override fun CreateView() {
        val viewState: DemoFragmentViewState by viewModel.collectAsState()
        MaterialTheme() {
            Counter(viewState)
        }
    }

    @androidx.compose.ui.tooling.preview.Preview(
        backgroundColor = 0xFFFFFFFF,
        device = "spec:width=1080px,height=1920px,dpi=440"
    )
    @Composable
    fun Counter(
        viewState: DemoFragmentViewState = DemoFragmentViewState(
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
                        onClick = { viewModel.decrementCounter() },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(text = "-")
                    }
                    Button(
                        onClick = { viewModel.incrementCounter() },
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