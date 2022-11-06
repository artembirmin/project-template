/*
 * ProjectTemplate
 *
 * Created by artembirmin on 5/11/2022.
 */

package com.incetro.projecttemplate.presentation.base.binding

import android.view.View
import androidx.databinding.BindingAdapter

object CommonBindingAdapters {

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun isViewVisible(view: View, isVisible: Boolean) {
        if (isVisible) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}