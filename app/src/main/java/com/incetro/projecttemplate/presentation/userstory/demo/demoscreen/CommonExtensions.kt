package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes


fun Context.attrRes(@AttrRes attrRes: Int): Int {
    val typedValue = TypedValue()
    val theme = this.theme
    theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue.resourceId
}
