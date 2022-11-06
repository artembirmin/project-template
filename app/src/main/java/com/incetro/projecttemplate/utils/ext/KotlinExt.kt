/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.projecttemplate.utils.ext

fun <R> Boolean.ifTrue(block: (Boolean) -> R): Boolean {
    if (this) run(block)
    return this
}

fun <R> Boolean.ifFalse(block: (Boolean) -> R): Boolean {
    if (!this) run(block)
    return this
}

fun String.removeWhitespaces() = filter { !it.isWhitespace() }