package com.incetro.projecttemplate.presentation.userstory.tabs.demoscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.incetro.projecttemplate.presentation.base.mvvm.view.BaseComposeFragment
import com.incetro.projecttemplate.presentation.base.mvvm.view.getInitParams
import com.incetro.projecttemplate.presentation.base.mvvm.view.provideInitParams
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.SavedStateViewModelFactoryImpl
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.lazyViewModelByFactory
import com.incetro.projecttemplate.presentation.ui.theme.AppTheme
import com.incetro.projecttemplate.presentation.userstory.tabs.demoflow.Tab1FlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.di.DemoComponent
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
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
            LaunchedEffect(true) {
                val isHomeTab = parentFragment?.parentFragment is Tab1FlowFragment
                val isFirstFlowFragmenInTab =
                    parentFragment?.parentFragment?.childFragmentManager?.backStackEntryCount == 0
                _viewModel.intent {
                    reduce {
                        state.copy(
                            isHomeTab = isHomeTab,
                            isFirstFlowFragmenInTab = isFirstFlowFragmenInTab
                        )
                    }
                }
            }
            val currentTabFlowFragmentName =
                parentFragment?.tag ?: "null"

            ScreenContent(
                viewState,
                _viewModel::onNavigateClick,
                _viewModel::onNavigateFlowInsideTab,
                _viewModel::onNavigateSeparateFlow,
                currentTabFlowFragmentName
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
    navigateToNextScreen: () -> Unit,
    navigateToNextFlow: () -> Unit,
    navigateToSeparateFlow: () -> Unit,
    currentTabFlowFragmentName: String
) {
    Scaffold { innerPadding ->
        Column(
            Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Screen number ${viewState.screenNumber}",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = navigateToNextScreen,
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Go to another screen")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Flow $currentTabFlowFragmentName",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = navigateToNextFlow,
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Go to another flow inside tab")
            }
            Button(
                onClick = navigateToSeparateFlow,
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Go to separate flow")
            }
        }
    }
}

@Preview
@Composable
fun ScreenContentPreview() {
    ScreenContent(
        DemoFragmentViewState(),
        {},
        {},
        {},
        "currentTabFragmentName"
    )
}