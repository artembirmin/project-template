/*
 * ProjectTemplate
 *
 * Created by artembirmin on 5/11/2022.
 */

package com.incetro.projecttemplate.common.di.componentmanager

/**
 * Provides Dagger component.
 */
interface ComponentProvider {

    /**
     * Returns [clazz] instance. [clazz] is Dagger component class.
     */
    fun <T> getComponent(clazz: Class<T>): T
}