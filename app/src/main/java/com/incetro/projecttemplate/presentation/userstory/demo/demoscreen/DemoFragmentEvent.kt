/*
 * ProjectTemplate
 *
 * Created by artembirmin on 12/8/2023.
 */

package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

sealed class DemoFragmentEvent {
    object IncreaseCounter : DemoFragmentEvent()
    object DecreaseCounter : DemoFragmentEvent()
}
