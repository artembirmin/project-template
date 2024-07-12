/*
 * ProjectTemplate
 *
 * Created by artembirmin on 11/8/2023.
 */

package com.incetro.firstapplication.entity.number

data class NumberFactResponse(
    val text: String,
    val found: Boolean,
    val number: Long,
)