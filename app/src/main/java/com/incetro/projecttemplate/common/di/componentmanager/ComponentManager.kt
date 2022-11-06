/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.projecttemplate.common.di.componentmanager

/**
 * Manages one Dagger component of type [T].
 * You need implement this in your Dagger component.
 * Call [getComponent] for get your component, then call inject method for injection.
 * [clazz] is java class of Dagger component.
 * [createAndSave] is component initialization function.
 */
abstract class ComponentManager<T>(
    private val clazz: Class<T>,
    private val createAndSave: () -> T
) {

    /**
     * Returns corresponding Dagger component from [ComponentsStore].
     */
    fun getComponent(): T {
        return if (isPresent) get() else createAndSave.invoke()
    }

    /**
     * Removes corresponding Dagger component from [ComponentsStore].
     */
    fun releaseComponent() {
        ComponentsManager.getInstance().removeComponent(clazz)
    }

    private val isPresent: Boolean
        get() = ComponentsManager.getInstance().hasComponent(clazz)

    private fun get(): T {
        return ComponentsManager.getInstance().getComponent(clazz)
    }
}