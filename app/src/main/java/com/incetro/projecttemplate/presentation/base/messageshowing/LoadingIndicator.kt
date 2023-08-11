package com.incetro.projecttemplate.presentation.base.messageshowing

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import com.incetro.projecttemplate.R
import java.lang.ref.WeakReference
import java.util.Timer
import java.util.TimerTask

class LoadingIndicator(private val activity: Activity) :
    Dialog(activity, R.style.Dialog_LoadingIndicator) {

    private val dismissRunnable = Runnable {
        super.dismiss()
    }

    private var loadingIndicatorCounter = 0
    private var isDismissed = true
    private var showedTime: Long = 0

    private val showRunnable = Runnable {
        if (!isDismissed) {
            showedTime = System.currentTimeMillis()
            super.show()
        }
    }
    private var timer: Timer? = null

    override fun show() {
        isDismissed = false
        loadingIndicatorCounter++
        postDelayed(showRunnable, DELAY_SHOW.toLong())
    }

    override fun dismiss() {
        val showingTime = System.currentTimeMillis() - showedTime
        isDismissed = true

        if (loadingIndicatorCounter > 0) {
            loadingIndicatorCounter--
        } else {
            loadingIndicatorCounter = 0
        }

        if (loadingIndicatorCounter == 0) {

            if (showedTime < DELAY_DISMISS) {
                postDelayed(dismissRunnable, showingTime - DELAY_DISMISS)
            } else {
                super.dismiss()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        ActivityComponent.Manager.getComponent().inject(this)
        super.onCreate(savedInstanceState)
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        setContentView(R.layout.layout_loading_indicator)

        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }

    private fun postDelayed(runnable: Runnable, millis: Long) {
        if (timer != null) {
            timer!!.cancel()
        }
        timer = Timer()
        timer!!.schedule(RunOnUiTimerTask(activity, runnable), millis)
    }

    override fun onDetachedFromWindow() {
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
        super.onDetachedFromWindow()
    }

    fun forceDismiss() {
        loadingIndicatorCounter = 0
        isDismissed = true
        showedTime = 0
        super.dismiss()
    }

    private class RunOnUiTimerTask(activity: Activity?, runnable: Runnable?) :
        TimerTask() {
        private val runnableRef: WeakReference<Runnable?> = WeakReference(runnable)
        private val activityRef: WeakReference<Activity?> = WeakReference(activity)
        override fun run() {
            if (activityRef.get() != null && runnableRef.get() != null && activityRef.get()?.isFinishing == false) {
                activityRef.get()!!.runOnUiThread(runnableRef.get())
            }
        }
    }

    companion object {
        private const val DELAY_SHOW = 150
        private const val DELAY_DISMISS = 600
    }

}