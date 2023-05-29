/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import android.graphics.Paint
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.LeadingMarginSpan
import android.util.TypedValue
import android.view.View
import android.widget.TextView
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
            val text =
                    "1234. You can participate if you have more than 3 days R lock at any of your transport"
            val number = "1234. "

            val textPaint: Paint = textView.paint
            val width = textPaint.measureText(number)

            val magrinSpan = LeadingMarginSpan.Standard(0, width.toInt())
            val span = SpannableString(text).apply {
                setSpan(
                    magrinSpan,
                    0,
                    text.length,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }

            textView.text = span
        }
    }

    private fun Int.toTextWithNumerationMargin(
        textView: TextView,
        number: String,
    ): CharSequence {
        return requireContext().getString(this).toTextWithNumerationMargin(textView, number)
    }

    private fun CharSequence.toTextWithNumerationMargin(
        textView: TextView,
        number: String,
    ): CharSequence {
        val textViewPaint: Paint = textView.paint
        val numerationWidth = textViewPaint.measureText(number)

        return SpannableString(this).apply {
            setSpan(
                numerationWidth,
                0,
                this.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun dip(dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resources.displayMetrics).toInt()
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    companion object {
        fun newInstance() = DemoFragment()
    }
}