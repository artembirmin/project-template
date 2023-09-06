/*
 * ProjectTemplate
 *
 * Created by artembirmin on 4/9/2023.
 */

package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.incetro.projecttemplate.presentation.base.messageshowing.AlertDialogState
import timber.log.Timber

@Composable
fun BaseAlertDialog(dialogState: AlertDialogState) {
    // TODO implement not cancelable dialog
    val properties: DialogProperties = DialogProperties()
    if (dialogState.isVisible) {
        AlertDialog(
            onDismissRequest = {
                dialogState.onDismiss?.invoke()
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