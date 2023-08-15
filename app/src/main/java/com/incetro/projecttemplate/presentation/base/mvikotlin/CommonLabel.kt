/*
 * todomvikotlin
 *
 * Created by artembirmin on 5/1/2023.
 */

package com.incetro.projecttemplate.presentation.base.mvikotlin

import com.arkivanov.mvikotlin.core.utils.JvmSerializable

sealed class CommonLabel : JvmSerializable {
    object ShowLoading : CommonLabel()
    object HideLoading : CommonLabel()
    data class ShowError(val error: Throwable) : CommonLabel()
    data class ShowMessageByToast(val message: String) : CommonLabel()
}
