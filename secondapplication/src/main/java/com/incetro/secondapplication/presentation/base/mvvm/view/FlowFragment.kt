package com.incetro.secondapplication.presentation.base.mvvm.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.BackTo
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.incetro.secondapplication.R
import com.incetro.secondapplication.common.di.qualifier.FlowNavigation
import com.incetro.secondapplication.common.navigation.FlowRouter
import com.incetro.secondapplication.databinding.LayoutContainerBinding
import timber.log.Timber
import javax.inject.Inject


abstract class FlowFragment : BaseFragment<LayoutContainerBinding>() {

    override val layoutRes: Int = R.layout.layout_container

    open val isFlowInsideTab: Boolean = false

    abstract var launchScreen: Screen

    private val currentFragment: Fragment?
        get() = childFragmentManager.findFragmentById(R.id.fragment_container)

    @Inject
    @FlowNavigation
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: FlowRouter

    private val navigator: Navigator by lazy {
        object : AppNavigator(requireActivity(), R.id.fragment_container, childFragmentManager) {
            override fun activityBack() {
                if (isFlowInsideTab) {
                    router.finishFlowInsideTab()
                } else {
                    router.finishFlow()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            setFlowLaunchScreen(launchScreen)
        }
    }

    private fun setFlowLaunchScreen(screen: Screen) {
        navigator.applyCommands(
            arrayOf(
                BackTo(null),
                Replace(screen)
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        changeTabVisibility(hasTabs)
    }

    private fun changeTabVisibility(isVisible: Boolean) {
        (parentFragment as? HasBottomNavigation)?.showTabs(isVisible)
    }

    override fun onBackPressed() {
        Timber.e("Flow onBackPressed. currentFragment = $currentFragment")
        (currentFragment as? BackPressedListener)?.onBackPressed() ?: super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}
