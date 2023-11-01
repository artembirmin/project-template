/*
 * ProjectTemplate
 *
 * Created by artembirmin on 31/10/2023.
 */

package com.incetro.projecttemplate.presentation.base.mvvm.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.BackTo
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.incetro.projecttemplate.R
import com.incetro.projecttemplate.common.di.qualifier.TabNavigation
import com.incetro.projecttemplate.common.navigation.TabRouter
import com.incetro.projecttemplate.databinding.LayoutContainerBinding
import timber.log.Timber
import javax.inject.Inject


abstract class TabFlowFragment : BaseFragment<LayoutContainerBinding>() {

    override val layoutRes: Int = R.layout.layout_container

    abstract var launchScreen: Screen

    private val currentFragment: Fragment?
        get() = childFragmentManager.findFragmentById(R.id.fragment_container)

    @Inject
    @TabNavigation
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: TabRouter

    private val navigator: Navigator by lazy {
        object : AppNavigator(requireActivity(), R.id.fragment_container, childFragmentManager) {
            override fun activityBack() {
                router.finishFlow()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.e("${this::class.java.simpleName}. navigator = $navigator, navHolder = $navigatorHolder, router = $router")
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
        Timber.e("TabFlow onBackPressed. currentFragment = $currentFragment")
        (currentFragment as? BackPressedListener)?.onBackPressed() ?: super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        Timber.e("setNavigator $navigator")
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        Timber.e("removeNavigator")
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}
