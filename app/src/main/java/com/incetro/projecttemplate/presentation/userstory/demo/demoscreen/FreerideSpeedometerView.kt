package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.github.anastr.speedviewlib.Speedometer
import com.github.anastr.speedviewlib.components.Style
import com.github.anastr.speedviewlib.components.indicators.NormalIndicator
import com.incetro.projecttemplate.R

open class FreerideSpeedometerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : Speedometer(context, attrs, defStyleAttr) {

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val speedometerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val speedometerRect = RectF()

    private var goodSpeedStartSection: Int = 0
    private var goodSpeedEndSection: Int = 0

    /**
     * change the color of the center circle.
     */
    var centerCircleColor: Int
        get() = circlePaint.color
        set(centerCircleColor) {
            circlePaint.color = centerCircleColor
            if (isAttachedToWindow)
                invalidate()
        }

    /**
     * change the width of the center circle.
     */
    var centerCircleRadius = dpTOpx(20f)
        set(centerCircleRadius) {
            field = centerCircleRadius
            if (isAttachedToWindow)
                invalidate()
        }

    init {
        init()
        initAttributeSet(context, attrs)
    }

    override fun defaultGaugeValues() {}

    override fun defaultSpeedometerValues() {
        indicator = NormalIndicator(context)
        super.backgroundCircleColor = 0
        super.marksNumber = 8
    }

    private fun init() {
        speedometerPaint.style = Paint.Style.STROKE
        circlePaint.color = 0xFF444444.toInt()
        backgroundPaint.color = ContextCompat.getColor(context, R.color.colorLight2)
    }

    private fun initAttributeSet(context: Context, attrs: AttributeSet?) {
        if (attrs == null) {
            return
        }
        val a =
                context.theme.obtainStyledAttributes(attrs,
                    R.styleable.FreerideSpeedometerView,
                    0,
                    0)

        circlePaint.color =
                a.getColor(R.styleable.FreerideSpeedometerView_sv_centerCircleColor,
                    circlePaint.color)
        backgroundPaint.color =
                a.getColor(R.styleable.FreerideSpeedometerView_bgCircleColor, backgroundPaint.color)
        centerCircleRadius =
                a.getDimension(
                    R.styleable.FreerideSpeedometerView_sv_centerCircleRadius,
                    centerCircleRadius
                )
        val styleIndex = a.getInt(R.styleable.FreerideSpeedometerView_sv_sectionStyle, -1)
        if (styleIndex != -1)
            sections.forEach { it.style = Style.values()[styleIndex] }
        a.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldW: Int, oldH: Int) {
        super.onSizeChanged(w, h, oldW, oldH)

        updateBackgroundBitmap()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawSpeedUnitText(canvas)

        drawIndicator(canvas)
        canvas.drawCircle(size * .5f, size * .5f, centerCircleRadius, circlePaint)

        drawNotes(canvas)
    }

    fun updateGoodSpeedSections(goodSpeedStartSection: Int, goodSpeedEndSection: Int) {
        this.goodSpeedStartSection = goodSpeedStartSection
        this.goodSpeedEndSection = goodSpeedEndSection
    }

    override fun createBackgroundBitmapCanvas(): Canvas {
        if (size == 0)
            return Canvas()
        backgroundBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(backgroundBitmap)
        canvas.drawCircle(size * .5f, size * .5f, size / 2f, backgroundPaint)

        // to fix preview mode issue
        canvas.clipRect(0, 0, size, size)

        return canvas
    }

    override fun updateBackgroundBitmap() {
        val c = createBackgroundBitmapCanvas()

        val addPadding = dpTOpx(12f)

        sections.forEach {
            val risk = it.width * .5f + padding + it.padding + addPadding
            speedometerRect.set(risk, risk, size - risk, size - risk)
            speedometerPaint.strokeWidth = it.width
            speedometerPaint.color = it.color
            val startAngle = (getEndDegree() - getStartDegree()) * it.startOffset + getStartDegree()
            val sweepAngle =
                    (getEndDegree() - getStartDegree()) * it.endOffset - (startAngle - getStartDegree())
            speedometerPaint.strokeCap = Paint.Cap.ROUND
            c.drawArc(speedometerRect, startAngle, sweepAngle, false, speedometerPaint)
        }

        if (sections.isNotEmpty()) {
            val firstGoodSection = sections[goodSpeedStartSection]
            val lastGoodSection = sections[goodSpeedEndSection]

            val startAngle =
                    (getEndDegree() - getStartDegree()) * firstGoodSection.startOffset + getStartDegree()
            val sweepAngle =
                    (getEndDegree() - getStartDegree()) * lastGoodSection.endOffset - (startAngle - getStartDegree())

            val colorRes = R.color.colorGreen
            speedometerPaint.color = ContextCompat.getColor(context, colorRes)
            c.drawArc(speedometerRect, startAngle, sweepAngle, false, speedometerPaint)
        }

        drawMarks(c)

        if (tickNumber > 0)
            drawTicks(c)
        else
            drawDefMinMaxSpeedPosition(c)
    }

    fun setSpeed(speed: Float, moveDuration: Long = 500L) {
        speedTo(speed, moveDuration)
    }
}