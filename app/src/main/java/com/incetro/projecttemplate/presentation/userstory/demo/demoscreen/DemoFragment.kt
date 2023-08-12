package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.incetro.projecttemplate.R
import com.incetro.projecttemplate.databinding.FragmentDemoBinding
import com.incetro.projecttemplate.presentation.base.mvvm.BaseMVVMFragment
import com.incetro.projecttemplate.presentation.base.mvvm.BaseViewModel
import com.incetro.projecttemplate.presentation.userstory.demo.di.DemoComponent
import com.incetro.projecttemplate.utils.ext.lazyViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getViewState().collect {
                    with(binding) {
                        tvCounter.text = it.counter.toString()
                        tvNumberFact.text = it.numberFact
                    }
                }
            }
        }
        initViews()
    }

    private fun initViews() {
        with(binding) {
            btnIncrease.setOnClickListener {
                viewModel.obtainEvent(DemoFragmentEvent.IncreaseCounter)
            }
            btnDecrease.setOnClickListener {
                viewModel.obtainEvent(DemoFragmentEvent.DecreaseCounter)
            }
        }
    }

    companion object {
        fun newInstance() = DemoFragment()
    }
}