/*
 * ProjectTemplate
 *
 * Created by artembirmin on 11/8/2023.
 */

package com.incetro.projecttemplate.presentation.base.mvi

interface EventHandler<T> {
    fun obtainEvent(event: T)
}