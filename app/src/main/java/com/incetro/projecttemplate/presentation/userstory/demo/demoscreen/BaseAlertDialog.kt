/*
 * ProjectTemplate
 *
 * Created by artembirmin on 4/9/2023.
 */

package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.incetro.projecttemplate.presentation.base.messageshowing.AlertDialogState

@Composable
fun BaseAlertDialog(dialogState: AlertDialogState) {
    if (dialogState.isVisible) {
        AlertDialog(
            onDismissRequest = {
                dialogState.onDismiss?.invoke()
            },
            title = {
                Text(text = dialogState.title)
            },
            text = {
                Text(text = dialogState.text, Modifier.wrapContentHeight())
            },
            icon = {
                dialogState.icon?.let {
                    Icon(painter = painterResource(id = it), contentDescription = "Dialog icon")
                }
            },
            confirmButton = {
                if (dialogState.positiveText != null) {
                    TextButton(
                        onClick = {
                            dialogState.onPositiveClick?.invoke()
                            dialogState.onDismiss?.invoke()
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
                            dialogState.onDismiss?.invoke()
                        }) {
                        Text(text = stringResource(id = dialogState.negativeText))
                    }
                }
            }
        )
    }
}

@Composable
@Preview
fun BaseAlertDialogPreview() {
    BaseAlertDialog(dialogState = AlertDialogState(isVisible = true))
}
