/*
 * ProjectTemplate
 *
 * Created by artembirmin on 23/10/2023.
 */

package com.incetro.secondapplication.presentation.userstory.tabs.tabfragment

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pages
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.incetro.secondapplication.common.navigation.Screens

enum class BottomTab(val screen: FragmentScreen, val tabName: String, val icon: ImageVector) {
    FIRST(Screens.Tab1FlowScreen(), "Tab1", Icons.Default.VerifiedUser),
    SECOND(Screens.Tab2FlowScreen(), "Tab2", Icons.Default.Pages),
    THIRD(Screens.Tab3FlowScreen(), "Tab3", Icons.Default.Pages),
    FOURTH(Screens.Tab4FlowScreen(), "Tab4", Icons.Default.Pages),
}