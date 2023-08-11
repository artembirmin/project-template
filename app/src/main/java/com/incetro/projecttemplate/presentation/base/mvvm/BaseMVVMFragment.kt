package com.incetro.projecttemplate.presentation.base.mvvm

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import es.dmoral.toasty.Toasty


abstract class BaseMVVMFragment<TBinding : ViewDataBinding> : BaseFragment<TBinding>() {

    abstract fun getViewModel(): BaseViewModelBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
//        getViewModel().showDialog()
//            .observe(viewLifecycleOwner) { it?.let { showMessageByAlertDialog(it) } }
        getViewModel().isLoading().observe(viewLifecycleOwner) {
            it?.let {
                if (it) showProgress()
                else hideProgress()
            }
        }
        getViewModel().showErrorEvent().observe(viewLifecycleOwner) {
            it?.let { showError(it) }
        }
        getViewModel().showMessage().observe(viewLifecycleOwner) {
            it?.let { showMessage(it.text, it.icon, Toasty.LENGTH_SHORT) }
        }
    }

    override fun onBackPressed() {
        getViewModel().onBackPressed()
    }
}