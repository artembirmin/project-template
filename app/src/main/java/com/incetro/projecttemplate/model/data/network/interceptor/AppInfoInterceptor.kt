/*
 * ProjectTemplate
 *
 * Created by artembirmin on 15/11/2023.
 */

package com.incetro.projecttemplate.model.data.network.interceptor

import com.incetro.projecttemplate.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

private const val HEADER_PLATFORM = "X-Device-Platform"
private const val HEADER_VERSION = "X-App-Version"
private const val HEADER_LOCALE = "X-Locale"

@Singleton
class AppInfoInterceptor @Inject constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val locale = Locale.getDefault().language
        val builder = chain.request().newBuilder()
            .addHeader(HEADER_PLATFORM, "Android")
            .addHeader(HEADER_VERSION, BuildConfig.VERSION_NAME)
            .addHeader(HEADER_LOCALE, locale)

        val request = builder.build()
        return chain.proceed(request)
    }
}
