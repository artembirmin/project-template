/*
 * ProjectTemplate
 *
 * Created by artembirmin on 20/7/2022.
 */

package com.incetro.projecttemplate.model.network.api

import com.incetro.projecttemplate.entity.number.NumberFactResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface DemoApi {

    @GET("http://numbersapi.com/{number}")
    suspend fun getNumberFact(
        @Path("number") number: Int,
        @Header("Content-Type") contentType: String = "application/json",
    ): NumberFactResponse
}