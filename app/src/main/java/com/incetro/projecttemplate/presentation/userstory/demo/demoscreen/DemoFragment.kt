package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.incetro.projecttemplate.presentation.base.mvvm.view.BaseComposeFragment
import com.incetro.projecttemplate.presentation.base.mvvm.view.getInitParams
import com.incetro.projecttemplate.presentation.base.mvvm.view.provideInitParams
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.SavedStateViewModelFactoryImpl
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.lazyViewModelByFactory
import com.incetro.projecttemplate.presentation.ui.theme.AppTheme
import com.incetro.projecttemplate.presentation.userstory.demo.di.DemoComponent
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
            ScreenContent(viewState)
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
fun ScreenContent(viewState: DemoFragmentViewState) {

}

@Preview
@Composable
fun ScreenContentPreview() {
    ScreenContent(DemoFragmentViewState())
}