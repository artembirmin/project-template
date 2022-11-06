/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.projecttemplate.utils

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

object SpannableUtils {
    fun createBoldTextPart(
        text: String,
        partToBold: String
    ): CharSequence {
        if (text.isEmpty() || partToBold.isEmpty() || !text.contains(partToBold)) {
            return text
        }

        val spannableString = SpannableString(text)
        val startPosition = text.indexOf(partToBold, ignoreCase = true)
        val endPosition = startPosition + partToBold.length
        spannableString.setSpan(
            StyleSpan(Typeface.BOLD),
            startPosition,
            endPosition,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return spannableString
    }

    fun createColoredTextPart(
        text: String,
        partToColor: String,
        @ColorRes color: Int,
        context: Context,
        isBold: Boolean = false
    ): CharSequence {
        if (text.isEmpty() || partToColor.isEmpty() || !text.contains(partToColor)) {
            return text
        }
        val spannableString = SpannableString(text)
        val colorInt = ContextCompat.getColor(context, color)
        val startPosition = text.indexOf(partToColor, ignoreCase = true)
        val endPosition = startPosition + partToColor.length
        spannableString.setSpan(
            ForegroundColorSpan(colorInt),
            startPosition,
            endPosition,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        if (isBold) {
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                startPosition,
                endPosition,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return spannableString
    }

    fun createColoredText(
        text: String,
        @ColorRes color: Int,
        context: Context,
    ): CharSequence {
        if (text.isEmpty()) {
            return text
        }
        val spannableString = SpannableString(text)
        val colorInt = ContextCompat.getColor(context, color)
        val startPosition = 0
        val endPosition = text.lastIndex
        spannableString.setSpan(
            ForegroundColorSpan(colorInt),
            startPosition,
            endPosition,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return spannableString
    }
}