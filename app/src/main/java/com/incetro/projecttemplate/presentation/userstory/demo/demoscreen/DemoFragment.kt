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
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.incetro.projecttemplate.presentation.base.messageshowing.AlertDialogState
import com.incetro.projecttemplate.presentation.base.mvvm.BaseMVVMFragment
import com.incetro.projecttemplate.presentation.base.mvvm.BaseViewModel
import com.incetro.projecttemplate.presentation.base.mvvm.SavedStateViewModelFactoryImpl
import com.incetro.projecttemplate.presentation.base.mvvm.ViewState
import com.incetro.projecttemplate.presentation.base.mvvm.lazyViewModelByFactory
import com.incetro.projecttemplate.presentation.userstory.demo.di.DemoComponent
import org.orbitmvi.orbit.compose.collectAsState
import javax.inject.Inject

class DemoFragment : BaseMVVMFragment() {

    @Inject
    lateinit var viewModelFactory: DemoViewModel.Factory

    private val viewModel: DemoViewModel by lazyViewModelByFactory {
        SavedStateViewModelFactoryImpl(
            this,
            viewModelFactory
        )
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun inject() = DemoComponent.Manager.getComponent().inject(this)

    override fun release() = Unit

    @Composable
    override fun CreateView() {
        val viewState: DemoFragmentViewState by viewModel.collectAsState()
        MaterialTheme {
            CollectBaseState(viewState = viewState)
            Counter(viewState)
        }
    }

    @Composable
    fun CollectBaseState(viewState: ViewState) {
        if (viewState.hasLoader) {

        }
        if (viewState.dialog.isVisible) {
            BaseAlertDialog(viewState.dialog)
        }
        if (viewState.toast.isVisible) {

        }
    }

    @Composable
    fun BaseAlertDialog(dialogState: AlertDialogState) {
        val openDialog = remember { mutableStateOf(true) }
        // TODO implement not cancelable dialog
        val properties: DialogProperties = DialogProperties()

        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = dialogState.title)
                },
                text = {
                    Text(text = dialogState.text)
                },
                icon = {
                    dialogState.icon?.let {
                        Icon(bitmap = ImageBitmap.imageResource(id = it), "Dialog image")
                    }
                },
                confirmButton = {
                    if (dialogState.positiveText != null) {
                        TextButton(
                            onClick = {
                                dialogState.onPositiveClick?.invoke()
                                openDialog.value = false
                            }) {
                            Text(text = stringResource(id = dialogState.positiveText))
                        }
                    }
                },
                dismissButton = {
                    if (dialogState.negativeText != null) {
                        TextButton(
                            onClick = {
                                dialogState.onNegativeClick?.invoke()
                                openDialog.value = false

                            }) {
                            Text(text = stringResource(id = dialogState.negativeText))
                        }
                    }
                }
            )
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

    companion object {
        fun newInstance() = DemoFragment()
    }
}