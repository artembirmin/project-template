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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.incetro.projecttemplate.presentation.base.mvvm.view.BaseMVVMFragment
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.SavedStateViewModelFactoryImpl
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.lazyViewModelByFactory
import com.incetro.projecttemplate.presentation.userstory.demo.di.DemoComponent
import org.orbitmvi.orbit.compose.collectAsState
import javax.inject.Inject

class DemoFragment : BaseMVVMFragment() {

    @Inject
    lateinit var viewModelFactory: DemoViewModel.Factory

    private val _viewModel: DemoViewModel by lazyViewModelByFactory {
        SavedStateViewModelFactoryImpl(
            this,
            viewModelFactory
        )
    }

    override fun getViewModel(): DemoViewModel = _viewModel

    override fun inject() = DemoComponent.Manager.getComponent().inject(this)

    override fun release() = Unit

    @Composable
    override fun CreateView() {
        val viewState: DemoFragmentViewState by _viewModel.collectAsState()
        MaterialTheme {
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
                        onClick = { _viewModel.decrementCounter() },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(text = "-")
                    }
                    Button(
                        onClick = { _viewModel.incrementCounter() },
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

    companion object {
        fun newInstance() = DemoFragment()
    }
}