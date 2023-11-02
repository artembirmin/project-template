/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.projecttemplate.presentation.userstory.start

import com.incetro.projecttemplate.common.di.activity.ActivityComponent
import com.incetro.projecttemplate.common.di.componentmanager.ComponentManager
import com.incetro.projecttemplate.common.di.componentmanager.ComponentsManager
import com.incetro.projecttemplate.common.di.scope.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    dependencies = [ActivityComponent::class],

    )
interface StartComponent {
    fun inject(startFragment: StartFragment)

    @Component.Builder
    interface Builder {
        fun activityComponent(component: ActivityComponent): Builder
        fun build(): StartComponent
    }

    object Manager : ComponentManager<StartComponent>(
        clazz = StartComponent::class.java,
        createAndSave = {
            val componentManager = ComponentsManager.getInstance()
            val activityComponent = ActivityComponent.Manager.getComponent()
            val component = DaggerStartComponent.builder()
                .activityComponent(activityComponent)
                .build()
            componentManager.addComponent(component)
        })
}