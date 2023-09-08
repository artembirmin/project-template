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


abstract class ViewState : Parcelable {
    open var loaderState: LoaderState = LoaderState()
    open var dialogState: AlertDialogState = AlertDialogState()

    abstract fun copyState(): ViewState
}

data class LoaderState(val hasLoading: Boolean = false, val isCancellable: Boolean = false) {
    companion object {
        val Loading = LoaderState(hasLoading = true)
        val Dismiss = LoaderState(hasLoading = false)
    }
}

fun <S : ViewState> S.updateDialog(reduce: (AlertDialogState) -> AlertDialogState): S {
    return this.copyState().apply {
        dialogState = reduce(this.dialogState)
    } as S
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