/*
 * ProjectTemplate
 *
 * Created by artembirmin on 16/9/2023.
 */

package com.incetro.secondapplication.presentation.ui.view

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import com.incetro.secondapplication.presentation.base.messageshowing.AlertDialogState
import com.incetro.secondapplication.presentation.base.messageshowing.DialogString

@Composable
fun BaseAlertDialog(dialogState: AlertDialogState) {
    if (dialogState.isVisible) {
        AlertDialog(
            onDismissRequest = {
                dialogState.onDismiss?.invoke()
            },
            title = {
                val dialogTitle = when (val title = dialogState.title) {
                    is DialogString.AnnotatedStringText -> title.value
                    is DialogString.StringText -> buildAnnotatedString { this.append(title.value) }
                    is DialogString.StringResText -> buildAnnotatedString {
                        this.append(
                            stringResource(id = title.value)
                        )
                    }

                    null -> null
                }
                if (dialogTitle != null) {
                    Text(text = dialogTitle)
                }
            },
            text = {
                val dialogText = when (val text = dialogState.text) {
                    is DialogString.AnnotatedStringText -> text.value
                    is DialogString.StringText -> buildAnnotatedString { this.append(text.value) }
                    is DialogString.StringResText -> buildAnnotatedString {
                        this.append(
                            stringResource(id = text.value)
                        )
                    }

                    null -> null
                }
                if (dialogText != null) {
                    Text(text = dialogText, Modifier.wrapContentHeight())
                }
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
