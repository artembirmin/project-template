/*
 * ProjectTemplate
 *
 * Created by artembirmin on 23/10/2023.
 */

package com.incetro.projecttemplate.presentation.userstory.tabs.tabfragment

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pages
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.incetro.projecttemplate.common.navigation.Screens

enum class BottomTab(val screen: FragmentScreen, val icon: ImageVector) {
    FIRST(Screens.Tab1FlowScreen(), Icons.Default.VerifiedUser),
    SECOND(Screens.Tab2FlowScreen(), Icons.Default.Pages),
    THIRD(Screens.Tab3FlowScreen(), Icons.Default.Pages),
    FOURTH(Screens.Tab4FlowScreen(), Icons.Default.Pages),
}