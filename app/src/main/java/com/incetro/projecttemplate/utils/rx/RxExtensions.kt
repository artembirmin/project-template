/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.projecttemplate.utils.rx

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable

inline fun <T, R> Single<List<T>>.listMap(crossinline mapper: (T) -> R): Single<List<R>> =
    map { it.map(mapper) }

fun Disposable?.unsubscribeSafe() {
    if (this?.isDisposed == false) {
        this.dispose()
    }
}