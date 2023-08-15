/*
 * todomvikotlin
 *
 * Created by artembirmin on 26/1/2023.
 */

package com.incetro.projecttemplate.presentation.base.mvikotlin

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.databinding.ViewDataBinding
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.essentyLifecycle
import com.arkivanov.essenty.statekeeper.stateKeeper
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.view.ViewEvents
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.events
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.rx.Disposable
import com.arkivanov.mvikotlin.rx.Observer
import com.arkivanov.mvikotlin.rx.internal.PublishSubject
import com.incetro.projecttemplate.utils.ext.removeStore
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

abstract class BaseStoreFragment<Binding : ViewDataBinding> : BaseFragment<Binding>(),
    ViewEvents<BackPressedStore.BackPressedIntent> {

    protected abstract val store: Store<*, *, CommonLabel>

    protected abstract val storeName: String

    protected val stateKeeper by lazy { stateKeeper() }

    @Inject
    lateinit var storeInstanceKeeper: InstanceKeeper

    @Inject
    lateinit var backPressedStore: BackPressedStore
    private val backPressedObservable = PublishSubject<BackPressedStore.BackPressedIntent>()
    private var hasBackPressedSubscribers = false

    override fun events(observer: Observer<BackPressedStore.BackPressedIntent>): Disposable =
        backPressedObservable.subscribe(observer)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lifecycle = viewLifecycleOwner.essentyLifecycle()


        bind(lifecycle, BinderLifecycleMode.CREATE_DESTROY, mainDispatcher) {
            this@BaseStoreFragment.events bindTo backPressedStore
            backPressedStore.labels.filter {
                hasBackPressedSubscribers.not()
            } bindTo ::handleNavigationLabel
            store.labels bindTo ::handleCommonLabel
        }

        initStoreInLifecycle(lifecycle)
    }

    protected open fun initStoreInLifecycle(lifecycle: Lifecycle) {
        lifecycle.doOnCreate {
            store.init()
        }
    }

    protected fun <Intent : Any> Lifecycle.subscribeOnBackPressedLabels(
        store: Store<Intent, *, *>,
        mapper: (NavigationLabel.Exit) -> Intent
    ) {
        bind(this, BinderLifecycleMode.CREATE_DESTROY) {
            backPressedStore.labels.map(mapper) bindTo store
        }
        hasBackPressedSubscribers = true
    }

    private fun handleCommonLabel(commonLabel: CommonLabel) {
        when (commonLabel) {
            is NavigationLabel -> handleNavigationLabel(commonLabel)
            is CommonLabel.ShowMessageByToast -> showMessage(commonLabel.message)
            is CommonLabel.HideLoading -> hideProgress()
            is CommonLabel.ShowLoading -> showProgress()
            is CommonLabel.ShowError -> showError(commonLabel.error)
        }
    }

    private fun handleNavigationLabel(navigationLabel: NavigationLabel) {
        when (navigationLabel) {
            is NavigationLabel.BackTo -> router.backTo(navigationLabel.screen)
            is NavigationLabel.Exit -> router.exit()
            is NavigationLabel.NavigateTo -> router.navigateTo(navigationLabel.screen)
            is NavigationLabel.NewRootScreen -> router.newRootScreen(navigationLabel.screen)
            is NavigationLabel.ReplaceScreen -> router.replaceScreen(navigationLabel.screen)
        }
    }

    protected inline fun <reified T : Parcelable> getState(): T? {
        return stateKeeper.consume(storeName, T::class)
    }

    override fun onCloseScope() {
        storeInstanceKeeper.removeStore(key = storeName)
    }

    protected fun closeFragment() {
        backPressedObservable.onNext(BackPressedStore.BackPressedIntent.OnBackPressed)
    }

    override fun onBackPressed() {
        closeFragment()
    }
}