/*
 * ProjectTemplate
 *
 * Created by artembirmin on 9/11/2023.
 */

package com.incetro.secondapplication.presentation.userstory.demofragment.demoscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.incetro.secondapplication.presentation.base.mvvm.view.BaseComposeFragment
import com.incetro.secondapplication.presentation.base.mvvm.view.getInitParams
import com.incetro.secondapplication.presentation.base.mvvm.view.provideInitParams
import com.incetro.secondapplication.presentation.base.mvvm.viewmodel.SavedStateViewModelFactoryImpl
import com.incetro.secondapplication.presentation.base.mvvm.viewmodel.lazyViewModelByFactory
import com.incetro.secondapplication.presentation.ui.theme.AppTheme
import com.incetro.secondapplication.presentation.userstory.demofragment.di.DemoComponent
import org.orbitmvi.orbit.compose.collectAsState
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

    @Composable
    override fun CreateView() {
        val viewState: DemoFragmentViewState by _viewModel.collectAsState()
        AppTheme {
            ScreenContent(
                viewState
            )
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

@Composable
fun ScreenContent(
    viewState: DemoFragmentViewState,
) {
    Scaffold { innerPadding ->
        Box(Modifier.padding(innerPadding))
    }
}

@Preview
@Composable
fun ScreenContentPreview() {
    ScreenContent(
        DemoFragmentViewState()
    )
}