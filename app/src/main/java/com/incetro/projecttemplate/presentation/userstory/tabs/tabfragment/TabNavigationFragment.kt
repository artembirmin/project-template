/*
 * Ruvpro
 *
 * Created by artembirmin on 4/5/2022.
 */

package com.incetro.projecttemplate.presentation.userstory.tabs.tabfragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.incetro.projecttemplate.R
import com.incetro.projecttemplate.common.di.activity.ActivityComponent
import com.incetro.projecttemplate.databinding.FragmentMainNavigationBinding
import com.incetro.projecttemplate.presentation.base.mvvm.view.BackPressedListener
import com.incetro.projecttemplate.presentation.base.mvvm.view.BaseFragment
import com.incetro.projecttemplate.presentation.base.mvvm.view.HasBottomNavigation
import com.incetro.projecttemplate.presentation.userstory.tabs.demoflow.Tab1FlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.demoflow.Tab2FlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.demoflow.Tab3FlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.demoflow.Tab4FlowFragment
import com.incetro.projecttemplate.utils.ext.visible
import timber.log.Timber

class TabNavigationFragment : BaseFragment<FragmentMainNavigationBinding>(),
    HasBottomNavigation {

    override val layoutRes = R.layout.fragment_main_navigation

    private val currentTabFlowFragment: Fragment?
        get() = childFragmentManager.fragments.firstOrNull { !it.isHidden }

    override fun inject() = ActivityComponent.Manager.getComponent().inject(this)

    override fun release() = Unit
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            navigationMenu.setOnItemSelectedListener { item ->
                if (!item.isChecked) {
                    when (item.itemId) {
                        R.id.menu_tab1 -> selectTab(BottomTab.FIRST)
                        R.id.menu_tab2 -> selectTab(BottomTab.SECOND)
                        R.id.menu_tab3 -> selectTab(BottomTab.THIRD)
                        R.id.menu_tab4 -> selectTab(BottomTab.FOURTH)
                    }
                }
                true
            }
        }

        selectTab(
            when (currentTabFlowFragment?.tag) {
                BottomTab.FIRST.screen.screenKey -> BottomTab.FIRST
                BottomTab.SECOND.screen.screenKey -> BottomTab.SECOND
                BottomTab.THIRD.screen.screenKey -> BottomTab.THIRD
                BottomTab.FOURTH.screen.screenKey -> BottomTab.FOURTH
                else -> BottomTab.FIRST
            }
        )
    }

    private fun selectTab(tab: BottomTab) {
        val currentFragment = currentTabFlowFragment
        val newFragment = childFragmentManager.findFragmentByTag(tab.screen.screenKey)

        if (currentFragment != null && newFragment != null && currentFragment == newFragment) return

        childFragmentManager.beginTransaction().apply {
            if (newFragment == null) add(
                R.id.mainScreenContainer,
                tab.screen.createFragment(childFragmentManager.fragmentFactory),
                tab.screen.screenKey
            )

            currentFragment?.let {
                hide(it)
                setMaxLifecycle(it, Lifecycle.State.STARTED) // for call onPause
            }
            newFragment?.let {
                show(it)
                setMaxLifecycle(it, Lifecycle.State.RESUMED) // for call onResume
            }
        }.commitNow()
    }

    override fun showTabs(show: Boolean) {
        binding.bottomAppBar.visible(true)
    }

    override fun onBackPressed() {
        val hasOpenInnerFragments =
            (currentTabFlowFragment?.childFragmentManager?.backStackEntryCount ?: 0) > 0
        val flowFragmentBackStackSize =
            currentTabFlowFragment?.childFragmentManager?.backStackEntryCount
        val fragmentBackStackSize =
            currentTabFlowFragment?.childFragmentManager?.fragments?.firstOrNull()?.childFragmentManager?.backStackEntryCount
                ?: 0

        val tabFlowBackPressedListener = currentTabFlowFragment as? BackPressedListener

        Timber.e("TabNavFragment onBackPressed hasOpenTabs = $hasOpenInnerFragments")
        if (hasOpenInnerFragments) {
            tabFlowBackPressedListener?.onBackPressed()
            return
        } else if (fragmentBackStackSize > 0) {
            tabFlowBackPressedListener?.onBackPressed()
            return
        }

        when (currentTabFlowFragment) {
            is Tab1FlowFragment -> tabFlowBackPressedListener?.onBackPressed()
            is Tab2FlowFragment -> navigateToHome()
            is Tab3FlowFragment -> navigateToHome()
            is Tab4FlowFragment -> navigateToHome()
            else -> tabFlowBackPressedListener?.onBackPressed()
        }
    }

    private fun navigateToHome() {
        selectTab(BottomTab.FIRST)
        binding.navigationMenu.selectedItemId = R.id.menu_tab1
    }


    companion object {
        fun newInstance() = TabNavigationFragment()
    }
}