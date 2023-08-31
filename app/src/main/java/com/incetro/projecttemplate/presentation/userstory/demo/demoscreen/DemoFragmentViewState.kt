/*
 * ProjectTemplate
 *
 * Created by artembirmin on 12/8/2023.
 */

package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DemoFragmentViewState(
    val counter: Int = 0,
    val numberFact: String = ""
) : Parcelable