/*
 * ProjectTemplate
 *
 * Created by artembirmin on 4/9/2023.
 */

package com.incetro.firstapplication.presentation.base.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.incetro.firstapplication.R
import com.incetro.firstapplication.presentation.base.messageshowing.ErrorHandler
import es.dmoral.toasty.Toasty
import moxy.MvpDelegate
import moxy.MvpDelegateHolder
import javax.inject.Inject

abstract class BaseBottomSheetDialogFragment<Binding : ViewDataBinding> :
    BottomSheetDialogFragment(),
    MvpDelegateHolder {

    protected lateinit var binding: Binding

    /** Layout id from res/layout. */
    abstract val layoutRes: Int

    @Inject
    lateinit var errorHandler: ErrorHandler

    private val baseBottomSheetDialogFragmentDelegate: MvpDelegate<out BaseBottomSheetDialogFragment<*>> by lazy {
        MvpDelegate(this)
    }

    override fun getMvpDelegate(): MvpDelegate<out BaseBottomSheetDialogFragment<*>> =
        baseBottomSheetDialogFragmentDelegate

    /**
     * True, when [onSaveInstanceState] called.
     */
    private var isInstanceStateSaved: Boolean = false

    abstract fun inject()
    abstract fun release()

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        mvpDelegate.onCreate(savedInstanceState)
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
        mvpDelegate.onAttach()
        isInstanceStateSaved = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mvpDelegate.onSaveInstanceState(outState)
        mvpDelegate.onDetach()
        isInstanceStateSaved = true
    }

    override fun onStop() {
        super.onStop()
        mvpDelegate.onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mvpDelegate.onDetach()
        mvpDelegate.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (needCloseScope()) {
            mvpDelegate.onDestroy()
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
            else -> isRealRemoving
        }

    private val isRealRemoving: Boolean =
        (isRemoving && !isInstanceStateSaved) //because isRemoving == true for fragment in backstack on screen rotation
                || ((parentFragment as? BaseComposeFragment)?.isRealRemoving() ?: false)

    fun showToastMessage(message: String, icon: Int?, length: Int?) {
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
}