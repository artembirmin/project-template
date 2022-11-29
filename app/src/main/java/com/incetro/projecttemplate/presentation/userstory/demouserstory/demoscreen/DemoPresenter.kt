/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.projecttemplate.presentation.userstory.demouserstory.demoscreen

import com.incetro.projecttemplate.common.navigation.AppRouter
import com.incetro.projecttemplate.presentation.base.fragment.BasePresenter
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class DemoPresenter @Inject constructor(
    private val router: AppRouter,
) : BasePresenter<DemoView>() {

    override fun onFirstViewAttach() {

    }

    override fun onBackPressed() {
        router.exit()
    }
}