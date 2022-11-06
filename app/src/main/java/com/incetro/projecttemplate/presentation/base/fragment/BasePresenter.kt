/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.projecttemplate.presentation.base.fragment


import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import com.incetro.projecttemplate.presentation.base.BaseView
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter

/**
 * Contains basic functionality for [Fragment] presenter.
 */
abstract class BasePresenter<View : BaseView> : MvpPresenter<View>() {

    /**
     * [CompositeDisposable] instance.
     */
    private val compositeDisposable = CompositeDisposable()

    private val compositeDisposableView = CompositeDisposable()

    @CallSuper
    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    @CallSuper
    override fun destroyView(view: View) {
        super.destroyView(view)
        compositeDisposableView.clear()
    }

    open fun onBackPressed() {
    }

    /**
     * Adding [Disposable] in [CompositeDisposable].
     */
    protected fun Disposable.addDisposable(): Disposable {
        compositeDisposable.add(this)
        return this
    }

    protected fun Disposable.addToViewDisposable(): Disposable {
        compositeDisposableView.add(this)
        return this
    }
}