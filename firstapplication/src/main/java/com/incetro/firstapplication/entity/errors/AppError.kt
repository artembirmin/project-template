/*
 * ProjectTemplate
 *
 * Created by artembirmin on 5/11/2022.
 */

package com.incetro.firstapplication.entity.errors

data class AppError(
    val error: Throwable,
    val payload: Any? = null
)