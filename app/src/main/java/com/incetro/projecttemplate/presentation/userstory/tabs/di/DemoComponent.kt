/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.projecttemplate.presentation.userstory.tabs.di

import com.incetro.projecttemplate.common.di.activity.ActivityComponent
import com.incetro.projecttemplate.common.di.componentmanager.ComponentManager
import com.incetro.projecttemplate.common.di.componentmanager.ComponentsManager
import com.incetro.projecttemplate.common.di.scope.FeatureScope
import com.incetro.projecttemplate.presentation.userstory.tabs.demoflow.FlowFragment1
import com.incetro.projecttemplate.presentation.userstory.tabs.demoflow.Tab1FlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.demoflow.Tab2FlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.demoflow.Tab3FlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.demoflow.Tab4FlowFragment
import com.incetro.projecttemplate.presentation.userstory.tabs.demoscreen.DemoFragment
import dagger.Component

@FeatureScope
@Component(
    dependencies = [ActivityComponent::class],
    modules = [
        DemoModule::class
    ]
)
interface DemoComponent {
    fun inject(demoFragment: DemoFragment)
    fun inject(tab1FlowFragment: Tab1FlowFragment)
    fun inject(tab2FlowFragment: Tab2FlowFragment)
    fun inject(tab3FlowFragment: Tab3FlowFragment)
    fun inject(tab4FlowFragment: Tab4FlowFragment)
    fun inject(flowFragment1: FlowFragment1)

    @Component.Builder
    interface Builder {
        fun activityComponent(component: ActivityComponent): Builder
        fun build(): DemoComponent
    }

    object Manager : ComponentManager<DemoComponent>(
        clazz = DemoComponent::class.java,
        createAndSave = {
            val componentManager = ComponentsManager.getInstance()
            val activityComponent = ActivityComponent.Manager.getComponent()
            val component = DaggerDemoComponent.builder()
                .activityComponent(activityComponent)
                .build()
            componentManager.addComponent(component)
        })
}