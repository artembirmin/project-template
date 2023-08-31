/*
 * ProjectTemplate
 *
 * Created by artembirmin on 29/8/2023.
 */

package com.incetro.projecttemplate.utils.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.incetro.projecttemplate.presentation.userstory.demo.demoscreen.CommonSideEffect
import org.orbitmvi.orbit.ContainerHost


@Composable
public fun <STATE : Any>
        ContainerHost<STATE, CommonSideEffect>.collectCommonSideEffectsAsState(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED
): State<CommonSideEffect> {
    val stateFlow = container.sideEffectFlow
    val lifecycleOwner = LocalLifecycleOwner.current

    val stateFlowLifecycleAware = remember(stateFlow, lifecycleOwner) {
        stateFlow.flowWithLifecycle(lifecycleOwner.lifecycle, lifecycleState)
    }

    return stateFlowLifecycleAware.collectAsState(CommonSideEffect.None)
}
