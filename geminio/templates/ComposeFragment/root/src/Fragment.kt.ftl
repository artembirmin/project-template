package ${packageName}

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.incetro.projecttemplate.presentation.base.mvvm.view.BaseComposeFragment
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.SavedStateViewModelFactoryImpl
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.lazyViewModelByFactory
import com.incetro.projecttemplate.presentation.ui.theme.AppTheme
import org.orbitmvi.orbit.compose.collectAsState
import javax.inject.Inject

class ${fragmentName}Fragment : BaseComposeFragment() {

    @Inject
    lateinit var viewModelFactory: ${fragmentName}ViewModel.Factory

    private val _viewModel: ${fragmentName}ViewModel by lazyViewModelByFactory {
        SavedStateViewModelFactoryImpl(
            this,
            viewModelFactory
        )
    }

    override fun getViewModel(): ${fragmentName}ViewModel = _viewModel

    override fun inject() = ${fragmentName}Component.Manager.getComponent().inject(this)

    override fun release() = Unit

    @Composable
    override fun CreateView() {
        val viewState: ${fragmentName}FragmentViewState by _viewModel.collectAsState()
        AppTheme {
            ${fragmentName}ScreenContent(
                viewState,
            )
        }
    }

    override fun onBackPressed() {
        _viewModel.onBackPressed()
    }

    companion object {
        fun newInstance() = ${fragmentName}Fragment()
    }
}

@Composable
fun ${fragmentName}ScreenContent(
    viewState: ${fragmentName}FragmentViewState,
) {
    Scaffold { innerPadding ->
        Box(Modifier.padding(innerPadding))
    }
}

@Preview
@Composable
fun ${fragmentName}ScreenContentPreview() {
    ${fragmentName}ScreenContent(
        ${fragmentName}FragmentViewState(),
    )
}