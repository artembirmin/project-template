/*
 * ProjectTemplate
 *
 * Created by artembirmin on 4/9/2023.
 */

package com.incetro.firstapplication.presentation.base.mvvm.viewmodel

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner

const val INITIAL_STATE_KEY = "DEFAULT_STATE_KEY"

class ViewModelFactory<T : ViewModel>(
    private val create: () -> T
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return create.invoke() as T
    }
}

interface ViewModelAssistedFactory<T : ViewModel> {
    fun create(handle: SavedStateHandle): T
}

class SavedStateViewModelFactoryImpl<out T : ViewModel>(
    owner: SavedStateRegistryOwner,
    private val assistedFactory: ViewModelAssistedFactory<T>,
    params: Parcelable? = null
) : AbstractSavedStateViewModelFactory(
    owner,
    Bundle().apply { putParcelable(INITIAL_STATE_KEY, params) }
) {
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return assistedFactory.create(handle) as T
    }
}

inline fun <reified T : ViewModel> Fragment.lazyViewModel(
    noinline create: () -> T
) = viewModels<T> { ViewModelFactory(create) }

inline fun <reified T : ViewModel> Fragment.lazyViewModelByFactory(
    noinline create: () -> ViewModelProvider.Factory
) = viewModels<T> { create.invoke() }

/**
 * It does not work. Why?
 */
inline fun <reified T : ViewModel> Fragment.lazyViewModelWithArgs(
    assistedFactory: ViewModelAssistedFactory<T>,
    params: Parcelable? = null
) = viewModels<T> { SavedStateViewModelFactoryImpl(this, assistedFactory, params) }

inline fun <reified T : ViewModel> Fragment.lazyStateViewModelPair(
    crossinline args: () -> Pair<ViewModelAssistedFactory<T>, Parcelable?>
) = viewModels<T> { SavedStateViewModelFactoryImpl(this, args().first, args().second) }
