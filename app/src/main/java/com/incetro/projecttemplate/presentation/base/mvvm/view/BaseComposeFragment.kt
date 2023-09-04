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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.incetro.projecttemplate.R
import com.incetro.projecttemplate.app.AppActivity
import com.incetro.projecttemplate.common.di.componentmanager.ComponentManager
import com.incetro.projecttemplate.common.di.componentmanager.ComponentsStore
import com.incetro.projecttemplate.common.navigation.AppRouter
import com.incetro.projecttemplate.entity.errors.AppError
import com.incetro.projecttemplate.presentation.base.BaseView
import com.incetro.projecttemplate.presentation.base.messageshowing.AlertDialogState
import com.incetro.projecttemplate.presentation.base.messageshowing.ErrorHandler
import com.incetro.projecttemplate.presentation.base.messageshowing.LoadingIndicator
import com.incetro.projecttemplate.presentation.base.messageshowing.SideEffect
import com.incetro.projecttemplate.presentation.base.messageshowing.ToastMessageState
import com.incetro.projecttemplate.presentation.base.mvvm.viewmodel.BaseViewModel
import com.incetro.projecttemplate.presentation.userstory.demo.demoscreen.BaseAlertDialog
import es.dmoral.toasty.Toasty
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber
import javax.inject.Inject

/**
 * Contains basic functionality for all [Fragment]s.
 */
abstract class BaseComposeFragment : Fragment(), BaseView {

    @Inject
    lateinit var errorHandler: ErrorHandler

    @Inject
    lateinit var router: AppRouter

    abstract fun getViewModel(): BaseViewModel<out ViewState, out SideEffect>

    private val loadingIndicator: LoadingIndicator by lazy { LoadingIndicator(requireActivity()) }

    /**
     * True, when [onSaveInstanceState] called.
     */
    private var isInstanceStateSaved: Boolean = false

    @Composable
    abstract fun CreateView()

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

    protected open val viewCompositionStrategy =
        ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(viewCompositionStrategy)
            setContent {
                var sideEffects by remember { mutableStateOf<SideEffect>(SideEffect.None) }
                getViewModel().collectSideEffect {
                    Timber.e("Side effect $it")
                    sideEffects = it
                }
//
                CollectSideEffects(sideEffect = sideEffects)

                val viewState: ViewState by getViewModel().collectAsState()
                CollectBaseState(viewState = viewState)

                CreateView()
            }
        }
    }

    @Composable
    open fun CollectSideEffects(sideEffect: SideEffect) {
        Timber.e("CollectSideEffects = $sideEffect")
        when (sideEffect) {
            is AlertDialogState -> {
                BaseAlertDialog(sideEffect)
            }

            is ToastMessageState -> {}
            SideEffect.ShowLoading -> {

            }

            SideEffect.HideLoading -> {

            }

            is SideEffect.ErrorDialog -> {}
            SideEffect.None -> {}
        }
    }

    @Composable
    open fun CollectBaseState(viewState: ViewState) {
        if (viewState.hasLoader) {

        }
        if (viewState.dialog.isVisible) {
            BaseAlertDialog(viewState.dialog)
        }
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
            onCloseScope()
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
                || ((parentFragment as? BaseComposeFragment)?.isRealRemoving() ?: false)

    protected open fun onCloseScope() {}

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