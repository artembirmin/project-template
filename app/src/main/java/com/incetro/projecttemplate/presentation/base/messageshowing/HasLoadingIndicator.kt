/*
 * ProjectTemplate
 *
 * Created by artembirmin on 11/8/2023.
 */

package com.incetro.projecttemplate.presentation.base.messageshowing

interface HasLoadingIndicator {
    fun showProgress()
    fun hideProgress()
    fun hideProgressForce()
}