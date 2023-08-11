package com.incetro.projecttemplate.presentation.base.messageshowing

import androidx.annotation.DrawableRes

data class ToastMessageParams(
    val text: String,
    @DrawableRes val icon: Int? = null
)