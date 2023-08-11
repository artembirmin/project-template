/*
 * ProjectTemplate
 *
 * Created by artembirmin on 11/8/2023.
 */

package com.incetro.projecttemplate.presentation.userstory.demo.demoscreen

import com.incetro.projecttemplate.common.di.scope.FeatureScope
import com.incetro.projecttemplate.entity.number.NumberFactResponse
import com.incetro.projecttemplate.model.network.api.DemoApi
import javax.inject.Inject

@FeatureScope
class NumberFactRepositoryImpl @Inject constructor(private val demoApi: DemoApi) :
    NumberFactRepository {

    override suspend fun getNumberFact(number: Int): NumberFactResponse {
        return demoApi.getNumberFact(number)
    }
}
