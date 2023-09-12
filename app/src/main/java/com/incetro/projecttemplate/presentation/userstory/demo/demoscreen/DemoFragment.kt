package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.incetro.projecttemplate.presentation.base.mvvm.view.BaseComposeFragment
import com.incetro.projecttemplate.presentation.base.mvvm.view.getInitParams
import com.incetro.projecttemplate.presentation.base.mvvm.view.provideInitParams
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.SavedStateViewModelFactoryImpl
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.lazyViewModelByFactory
import com.incetro.projecttemplate.presentation.userstory.demo.di.DemoComponent
import org.orbitmvi.orbit.compose.collectAsState
import timber.log.Timber
import javax.inject.Inject

class DemoFragment : BaseComposeFragment() {

    @Inject
    lateinit var viewModelFactory: DemoViewModel.Factory

    private val _viewModel: DemoViewModel by lazyViewModelByFactory {
        SavedStateViewModelFactoryImpl(
            this,
            viewModelFactory,
            getInitParams<DemoFragmentViewState>()
        )
    }

    override fun getViewModel(): DemoViewModel = _viewModel

    override fun inject() = DemoComponent.Manager.getComponent().inject(this)

    override fun release() = Unit

    @Preview
    @Composable
    override fun CreateView() {
        val viewState: DemoFragmentViewState by _viewModel.collectAsState()
        MaterialTheme {
            Scaffold(topBar = { Toolbar() }) { innerPadding ->
                Counter(viewState, innerPadding)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun Toolbar(
        viewState: DemoFragmentViewState = DemoFragmentViewState(
            counter = 12,
            numberFact = "qqqqkqqqqqqqqqqqqqqqqq2wqqqqqq",
        )
    ) {
        TopAppBar(
            title = {
                Text(text = "TopAppBar Screen ${viewState.screenNo}")
            },
            navigationIcon = {
                IconButton(onClick = { _viewModel.onBackPressed() }) {
                    Icon(Icons.Filled.ArrowBack, "")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.DarkGray)
        )
    }


    @Composable
    fun Counter(
        viewState: DemoFragmentViewState = DemoFragmentViewState(
            counter = 12,
            numberFact = "qqqqkqqqqqqqqqqqqqqqqq2wqqqqqq"
        ),
        padding: PaddingValues
    ) {
        Timber.e("Padding = $padding")
        Surface(color = Color.LightGray, modifier = Modifier.padding(padding)) {
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

                Row(modifier = Modifier) {
                    Button(
                        onClick = { _viewModel.onCopyScreen() },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(text = "Copy")
                    }
                    if (viewState.screenNo == 1) {
//                        Button(
//                            onClick = { _viewModel.onChangeTheme() },
//                            modifier = Modifier.padding(horizontal = 16.dp)
//                        ) {
//                            Text(text = "Change theme")
//                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        _viewModel.onBackPressed()
    }

    companion object {
        fun newInstance(initialState: DemoFragmentViewState) =
            DemoFragment().provideInitParams(initialState) as DemoFragment
    }
}