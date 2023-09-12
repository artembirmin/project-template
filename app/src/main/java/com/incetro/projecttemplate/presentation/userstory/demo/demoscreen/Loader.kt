/*
 * ProjectTemplate
 *
 * Created by artembirmin on 8/9/2023.
 */

package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.incetro.projecttemplate.presentation.base.mvvm.view.LoaderState

@Composable
fun Loader(loaderState: LoaderState) {
    val showDialog = remember { mutableStateOf(true) }
    if (loaderState.hasLoading && showDialog.value) {
        Dialog(
            onDismissRequest = { showDialog.value = false },
            DialogProperties(
                dismissOnBackPress = loaderState.isCancellable,
                dismissOnClickOutside = loaderState.isCancellable
            )
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(White, shape = RoundedCornerShape(8.dp))
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview
@Composable
fun LoaderPreview() {
    Loader(loaderState = LoaderState(hasLoading = true))
}