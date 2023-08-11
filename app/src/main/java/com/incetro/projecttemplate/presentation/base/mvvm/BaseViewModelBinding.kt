package com.incetro.projecttemplate.presentation.base.mvvm

import androidx.lifecycle.LiveData
import com.incetro.projecttemplate.entity.errors.AppError
import com.incetro.projecttemplate.presentation.base.messageshowing.StylishDialogParams
import com.incetro.projecttemplate.presentation.base.messageshowing.ToastMessageParams


interface BaseViewModelBinding {

    fun onBackPressed()
    fun isLoading(): LiveData<Boolean>
    fun showErrorEvent(): SingleLiveEvent<AppError>
    fun showDialog(): SingleLiveEvent<StylishDialogParams>
    fun showMessage(): SingleLiveEvent<ToastMessageParams>

}