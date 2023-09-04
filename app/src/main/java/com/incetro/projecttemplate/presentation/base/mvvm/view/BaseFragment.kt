/*
 * ProjectTemplate
 *
 * Created by artembirmin on 4/9/2023.
 */

package com.incetro.projecttemplate.presentation.base.mvvm.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.incetro.projecttemplate.R
import com.incetro.projecttemplate.app.AppActivity
import com.incetro.projecttemplate.common.di.componentmanager.ComponentManager
import com.incetro.projecttemplate.common.di.componentmanager.ComponentsStore
import com.incetro.projecttemplate.entity.errors.AppError
import com.incetro.projecttemplate.presentation.base.BaseView
import com.incetro.projecttemplate.presentation.base.messageshowing.ErrorHandler
import com.incetro.projecttemplate.presentation.base.messageshowing.LoadingIndicator
import es.dmoral.toasty.Toasty
import moxy.MvpAppCompatFragment
import javax.inject.Inject

/**
 * Contains basic functionality for all [Fragment]s.
 */
abstract class BaseFragment<Binding : ViewDataBinding> : MvpAppCompatFragment(), BaseView {

    /**
     * Instance of [ViewDataBinding] class implementation for fragment.
     */
    protected lateinit var binding: Binding

    @Inject
    lateinit var errorHandler: ErrorHandler

    /** Layout id from res/layout. */
    abstract val layoutRes: Int

    private val loadingIndicator: LoadingIndicator by lazy { LoadingIndicator(requireActivity()) }

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
     * Called in [AppActivity.onBackPressed].
     */
    open fun onBackPressed() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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

    override fun showError(error: Throwable) {
        showError(AppError(error))
    }

    override fun showError(error: AppError) {
        errorHandler.showError(error, requireContext())
    }

    override fun showMessageByAlertDialog(
        @StringRes title: Int?,
        @StringRes message: Int?,
        @StringRes positiveText: Int,
        @StringRes negativeText: Int?,
        onPositiveButtonClick: (() -> Unit)?,
        onNegativeButtonClick: (() -> Unit)?,
        onDismiss: (() -> Unit)?
    ) {
        AlertDialog.Builder(requireContext())
            .setMessage(message?.let { requireContext().getString(it) })
            .apply {
                negativeText?.let { setNegativeButton(it) { _, _ -> onNegativeButtonClick?.invoke() } }
                title?.let { setTitle(requireContext().getString(it)) }
            }
            .setPositiveButton(positiveText) { _, _ -> onPositiveButtonClick?.invoke() }
            .setOnDismissListener { onDismiss?.invoke() }
            .create()
            .show()
    }

    override fun showMessage(message: String, icon: Int?, length: Int?) {
        val colorBG = ContextCompat.getColor(requireContext(), R.color.black_transparent_62)
        val colorText = ContextCompat.getColor(requireContext(), R.color.white)

        val hasIcon = icon != null
        val iconDrawable = icon?.let { ContextCompat.getDrawable(requireContext(), icon) }

        val duration =
            length ?: if (message.length > 30) Toasty.LENGTH_LONG else Toasty.LENGTH_SHORT

        val toasty = Toasty.custom(
            requireContext(),
            message,
            iconDrawable,
            colorBG,
            colorText,
            duration,
            hasIcon,
            false
        )

        toasty.show()
    }


    override fun showProgress() {
        loadingIndicator.show()
    }

    override fun hideProgress() {
        loadingIndicator.dismiss()
    }

    override fun hideProgressForce() {
        loadingIndicator.forceDismiss()
    }
}