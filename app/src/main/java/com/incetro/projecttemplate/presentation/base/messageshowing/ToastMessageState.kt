package com.incetro.projecttemplate.presentation.base.messageshowing

import androidx.annotation.DrawableRes

data class ToastMessageState(
    val isVisible: Boolean = false,
    val text: String = "",
    @DrawableRes val icon: Int? = null
)