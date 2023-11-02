package com.incetro.projecttemplate.presentation.userstory.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.incetro.projecttemplate.presentation.base.mvvm.view.BaseComposeFragment
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.SavedStateViewModelFactoryImpl
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.lazyViewModelByFactory
import com.incetro.projecttemplate.presentation.ui.theme.AppTheme
import org.orbitmvi.orbit.compose.collectAsState
import javax.inject.Inject

class StartFragment : BaseComposeFragment() {

    @Inject
    lateinit var viewModelFactory: StartViewModel.Factory

    private val _viewModel: StartViewModel by lazyViewModelByFactory {
        SavedStateViewModelFactoryImpl(
            this,
            viewModelFactory
        )
    }

    override fun getViewModel(): StartViewModel = _viewModel

    override fun inject() = StartComponent.Manager.getComponent().inject(this)

    override fun release() = Unit

    @Composable
    override fun CreateView() {
        val viewState: StartFragmentViewState by _viewModel.collectAsState()
        AppTheme {
            ScreenContent(
                onNavigateToTabsClick = _viewModel::onNavigateToTabsClick,
                onReplaceOnTabsClick = _viewModel::onReplaceOnTabsClick,
                onExitClick = _viewModel::onExitClick,
            )
        }
    }

    override fun onBackPressed() {
        _viewModel.onBackPressed()
    }

    companion object {
        fun newInstance() = StartFragment()
    }
}

@Composable
fun ScreenContent(
    onNavigateToTabsClick: () -> Unit,
    onReplaceOnTabsClick: () -> Unit,
    onExitClick: () -> Unit,
) {
    Scaffold { innerPadding ->
        Column(
            Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onNavigateToTabsClick,
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Go to tabs")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onReplaceOnTabsClick,
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Go to tabs as a root screen")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onExitClick,
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Exit")
            }
        }
    }
}

@Preview
@Composable
fun ScreenContentPreview() {
    ScreenContent(
        {},
        {},
        {},
    )
}