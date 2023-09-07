/*
 * ProjectTemplate
 *
 * Created by artembirmin on 7/9/2023.
 */

package com.incetro.projecttemplate.entity.errors

import com.google.gson.annotations.SerializedName

data class ErrorBody(
    val errors: String?,
    val code: Int,
    @SerializedName("extended_desc")
    val description: String?,
    val status: Boolean
)