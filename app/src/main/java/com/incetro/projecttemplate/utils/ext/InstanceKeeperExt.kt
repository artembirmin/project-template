/*
 * todomvikotlin
 *
 * Created by artembirmin on 21/1/2023.
 */

package com.incetro.projecttemplate.utils.ext

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.incetro.projecttemplate.BuildConfig

fun InstanceKeeper.removeStore(key: Any) {
    val removedStore = this.remove(key)
    if (removedStore != null) {
        removedStore.onDestroy()
    } else if (BuildConfig.DEBUG) {
        throw IllegalArgumentException("InstanceKeeper not contains key = $key")
    }
}