package ${packageName}

import com.github.terrakok.cicerone.Screen
import com.incetro.projecttemplate.common.navigation.Screens
import com.incetro.projecttemplate.presentation.base.mvvm.view.FlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.demoscreen.DemoFragmentViewState
import com.incetro.projecttemplate.presentation.userstory.tabs.di.DemoComponent

class ${flowFragmentName}FlowFragment(override val isFlowInsideTab: Boolean) : FlowFragment() {

    override var launchScreen: Screen = Screens.${launchFragmentName}Screen(${launchFragmentName}FragmentViewState(1))

    override fun inject() = ${flowFragmentName}Component.Manager.getComponent().inject(this)
    override fun release() =${flowFragmentName}Component.Manager.releaseComponent()

    companion object {
        fun newInstance(isFlowInsideTab: Boolean = false): ${flowFragmentName}FlowFragment =
            ${flowFragmentName}FlowFragment(isFlowInsideTab)
    }
}