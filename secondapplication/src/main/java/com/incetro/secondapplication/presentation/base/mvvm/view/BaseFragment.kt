/*
 * ProjectTemplate
 *
 * Created by artembirmin on 30/10/2023.
 */

package com.incetro.secondapplication.presentation.base.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.incetro.secondapplication.app.AppActivity
import com.incetro.secondapplication.common.di.componentmanager.ComponentManager
import com.incetro.secondapplication.common.di.componentmanager.ComponentsStore
import com.incetro.secondapplication.presentation.base.messageshowing.ErrorHandler
import moxy.MvpAppCompatFragment
import javax.inject.Inject

/**
 * Contains basic functionality for all [Fragment]s.
 */
abstract class BaseFragment<Binding : ViewDataBinding> : MvpAppCompatFragment(),
    BackPressedListener {

    /**
     * Instance of [ViewDataBinding] class implementation for fragment.
     */
    protected lateinit var binding: Binding

    @Inject
    lateinit var errorHandler: ErrorHandler

    /** Layout id from res/layout. */
    abstract val layoutRes: Int

    /**
     * True, when [onSaveInstanceState] called.
     */
    private var isInstanceStateSaved: Boolean = false

    /**
     * Does dependency injection.
     * Use [ComponentManager] implementation in dagger component and call [ComponentManager.getComponent].
     * Ex. SomeScreenComponent.ComponentManager.getComponent().inject(this)
     */
    abstract fun inject()

    /**
     * Removes corresponding dagger component from [ComponentsStore].
     * Use [ComponentManager] implementation in dagger component and call [ComponentManager.releaseComponent].
     * Ex. SomeScreenComponent.ComponentManager.releaseComponent()
     */
    abstract fun release()

    /**
     * Called in [AppActivity.onBackPressedClick].
     */
    override fun onBackPressed() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        isInstanceStateSaved = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        isInstanceStateSaved = true
    }

    override fun onDestroy() {
        super.onDestroy()
        if (needCloseScope()) {
            release()
        }
    }

    /**
     * Checks if the component needs to be released.
     */
    private fun needCloseScope(): Boolean =
        when {
            activity?.isChangingConfigurations == true -> false
            activity?.isFinishing == true -> true
            else -> isRealRemoving()
        }

    /**
     * `True` if current fragment removing now.
     */
    fun isRealRemoving(): Boolean =
        (isRemoving && !isInstanceStateSaved) //because isRemoving == true for fragment in backstack on screen rotation
                || ((parentFragment as? BaseFragment<*>)?.isRealRemoving() ?: false)
}