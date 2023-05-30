/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.github.anastr.speedviewlib.components.Style
import com.github.anastr.speedviewlib.components.indicators.ImageIndicator
import com.incetro.projecttemplate.R
import com.incetro.projecttemplate.databinding.FragmentDemoBinding
import com.incetro.projecttemplate.presentation.base.fragment.BaseFragment
import com.incetro.projecttemplate.presentation.userstory.demo.di.DemoComponent
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject
import kotlin.math.ceil

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
            initSpeedometer(binding)
            setSpeedRange(binding)
        }
    }


    private fun initSpeedometer(binding: FragmentDemoBinding) {
        val minSpeed = 1f
        val maxSpeed = 7f
        val speedDiapason = (maxSpeed - minSpeed).toInt()
        val tickNumber = if (speedDiapason > 10) 11 else speedDiapason + 1

        binding.speedometer.tickNumber = tickNumber
        binding.speedometer.maxSpeed = maxSpeed
        binding.speedometer.minSpeed = minSpeed
    }

    private fun setSpeedRange(binding: FragmentDemoBinding) {
        val maxGoodSpeed = 6f
        val minGoodSpeed = 2f
        val speedometerMaxSpeed = 7f
        val speedometerMinSpeed = 1f
        val context = binding.root.context

        val speedDiapason = (speedometerMaxSpeed - speedometerMinSpeed).toInt()

        val tubeColorRes = context.attrRes(R.attr.SpeedometerTubeColor)
        val grayColor = ContextCompat.getColor(context, tubeColorRes)

        val goodSpeedStartSection = ceil(minGoodSpeed - speedometerMinSpeed).toInt()
        val goodSpeedEndSection = ceil(maxGoodSpeed - speedometerMinSpeed).toInt() - 1

        binding.speedometer.updateGoodSpeedSections(goodSpeedStartSection, goodSpeedEndSection)

        binding.speedometer.clearSections()
        binding.speedometer.makeSections(speedDiapason, grayColor, Style.BUTT)

        val indicator =
                ContextCompat.getDrawable(context, R.drawable.ic_speedometer_indicator)
        indicator?.let {
            val imageIndicator = ImageIndicator(context, it)
            binding.speedometer.indicator = imageIndicator
        }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    companion object {
        fun newInstance() = DemoFragment()
    }
}