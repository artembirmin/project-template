/*
 * ProjectTemplate
 *
 * Created by artembirmin on 4/9/2023.
 */

package com.incetro.projecttemplate.presentation.base.mvvm.view

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.incetro.projecttemplate.presentation.base.messageshowing.AlertDialogState
import com.incetro.projecttemplate.presentation.userstory.demo.demoscreen.DemoFragmentViewState


abstract class ViewState : Parcelable {
//    open var commonStateValues: CommonStateValues = CommonStateValues()
    open var hasLoader: Boolean = false
    open var dialog: AlertDialogState = AlertDialogState()

    abstract fun copyState(): ViewState
}

data class CommonStateValues(
    var hasLoader: Boolean = false,
    var dialog: AlertDialogState = AlertDialogState()
)

fun <S : ViewState> S.updateDialog(reduce: (AlertDialogState) -> AlertDialogState): S {
    return this.copyState().apply {
        dialog = reduce(this.dialog)
    } as S
}

fun ViewState.updateLoader(hasLoading: Boolean): ViewState {
    this.hasLoader = hasLoading
    return this
}

/**
 * Saves init params in [bundle].
 * The key is `SomeFragmentInitParams::class.java.simpleName`.
 */
fun <T : ViewState> T.saveToBundle(bundle: Bundle) {
    val key = this::class.java.simpleName
    bundle.putParcelable(key, this)
}

/**
 * Returns init params whose class is [T].
 */
inline fun <reified T : ViewState> Fragment.getInitParams(): T {
    val key = T::class.java.simpleName
    return arguments?.getParcelable(key) ?: error("$key wasn't set")
}

/**
 * Saves init params to [Fragment.mArguments].
 */
fun <T : ViewState> Fragment.provideInitParams(initParams: T?): Fragment {
    return this.apply {
        arguments = Bundle().apply {
            initParams?.saveToBundle(this)
        }
    }
}