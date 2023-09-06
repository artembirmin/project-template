/*
 * ProjectTemplate
 *
 * Created by artembirmin on 4/9/2023.
 */

package com.incetro.projecttemplate.presentation.base.mvvm.view

import android.os.Bundle
import android.view.View
import es.dmoral.toasty.Toasty


abstract class BaseMVVMFragment : BaseComposeFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.lifecycleOwner = viewLifecycleOwner
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
            it?.let { showToastMessage(it.text, it.icon, Toasty.LENGTH_SHORT) }
        }
    }

    override fun onBackPressed() {
        getViewModel().onBackPressed()
    }
}