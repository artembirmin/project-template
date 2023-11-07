package ${packageName}

import com.incetro.projecttemplate.common.di.activity.ActivityComponent
import com.incetro.projecttemplate.common.di.componentmanager.ComponentManager
import com.incetro.projecttemplate.common.di.componentmanager.ComponentsManager
import com.incetro.projecttemplate.common.di.scope.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    dependencies = [ActivityComponent::class],
    modules = [
        DemoModule::class
    ]
)
interface DemoComponent {
    fun inject(flowFragment: ${flowFragmentName}FlowFragment)
    fun inject(launchFragment: ${launchFragmentName}Fragment)

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
            val component = Dagger${flowFragmentName}Component.builder()
                .activityComponent(activityComponent)
                .build()
            componentManager.addComponent(component)
        })
}