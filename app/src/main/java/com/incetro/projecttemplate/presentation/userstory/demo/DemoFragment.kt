/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.projecttemplate.presentation.userstory.demo

import android.os.Bundle
import android.view.View
import com.incetro.projecttemplate.R
import com.incetro.projecttemplate.databinding.FragmentDemoBinding
import com.incetro.projecttemplate.presentation.base.fragment.BaseFragment
import com.incetro.projecttemplate.presentation.userstory.demo.di.DemoComponent
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class DemoFragment : BaseFragment<FragmentDemoBinding>(), DemoView {

    override val layoutRes = R.layout.fragment_demo

    @Inject
    @InjectPresenter
    lateinit var presenter: DemoPresenter

    @ProvidePresenter
    fun providePresenter(): DemoPresenter = presenter

    override fun inject() = DemoComponent.Manager.getComponent().inject(this)
    override fun release() = DemoComponent.Manager.releaseComponent()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {

        }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    companion object {
        fun newInstance() = DemoFragment()
    }
}