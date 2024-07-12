/*
 * ProjectTemplate
 *
 * Created by artembirmin on 9/11/2023.
 */

package com.incetro.secondapplication.presentation.userstory.demofragment.di

import com.incetro.secondapplication.common.di.activity.ActivityComponent
import com.incetro.secondapplication.common.di.componentmanager.ComponentManager
import com.incetro.secondapplication.common.di.componentmanager.ComponentsManager
import com.incetro.secondapplication.common.di.scope.FeatureScope
import com.incetro.secondapplication.presentation.userstory.demofragment.demoscreen.DemoFragment
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