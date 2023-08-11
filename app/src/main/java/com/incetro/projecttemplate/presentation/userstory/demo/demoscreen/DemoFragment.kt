package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import android.os.Bundle
import android.view.View
import com.incetro.projecttemplate.R
import com.incetro.projecttemplate.databinding.FragmentDemoBinding
import com.incetro.projecttemplate.presentation.base.mvvm.BaseMVVMFragment
import com.incetro.projecttemplate.presentation.base.mvvm.BaseViewModel
import com.incetro.projecttemplate.presentation.userstory.demo.di.DemoComponent
import com.incetro.projecttemplate.utils.ext.lazyViewModel
import javax.inject.Inject

sealed class DemoFragmentEvent {

}

data class DemoFragmentViewState(
    val counter: Int = 0,
    val numberFact: String = ""
)

class DemoFragment : BaseMVVMFragment<FragmentDemoBinding>() {

    override val layoutRes = R.layout.fragment_demo

    @Inject
    lateinit var viewModelFactory: DemoViewModel.Factory

    private val viewModel: DemoViewModel by lazyViewModel {
        viewModelFactory.create()
    }

    override fun getViewModel(): BaseViewModel<DemoFragmentEvent> = viewModel

    override fun inject() = DemoComponent.Manager.getComponent().inject(this)

    override fun release() = Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {

        }
    }

    companion object {
        fun newInstance() = DemoFragment()
    }
}