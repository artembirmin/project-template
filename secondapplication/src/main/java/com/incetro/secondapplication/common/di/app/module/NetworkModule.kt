/*
 * ProjectTemplate
 *
 * Created by artembirmin on 29/4/2022.
 */

package com.incetro.secondapplication.common.di.app.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.incetro.secondapplication.BuildConfig
import com.incetro.secondapplication.model.data.network.api.DemoApi
import com.incetro.secondapplication.model.data.network.interceptor.AppInfoInterceptor
import com.incetro.secondapplication.utils.FileLoggingTree
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    internal fun provideGson(): Gson {
        return GsonBuilder().setPrettyPrinting().create()
    }

    @Singleton
    @Provides
    internal fun provideLoggingInterceptor(gson: Gson): HttpLoggingInterceptor {
        val logLevel = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE

        val logger =
            object : HttpLoggingInterceptor.Logger by HttpLoggingInterceptor.Logger.DEFAULT {

                private val fileLoggingTree = Timber.forest().first { it is FileLoggingTree }

                override fun log(message: String) {
                    if (message.startsWith("{") || message.startsWith("[")) {
                        try {
                            val prettyPrintJson = gson.toJson(JsonParser.parseString(message))
                            printLog(prettyPrintJson)
                        } catch (m: JsonSyntaxException) {
                            printLog(message)
                        }
                    } else {
                        printLog(message)
                        return
                    }
                }

                private fun printLog(message: String) {
                    Platform.get().log(message)
                    fileLoggingTree.d(message)
                }
            }

        return HttpLoggingInterceptor(logger).apply { level = logLevel }
    }

    @Provides
    internal fun provideOkHttpBuilder(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
    }

    @Provides
    internal fun provideRetrofitBuilder(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    }

    @Singleton
    @Provides
    internal fun provideDemoApi(
        retrofitBuilder: Retrofit.Builder,
        okHttpClientBuilder: OkHttpClient.Builder,
        appInfoInterceptor: AppInfoInterceptor,
    ): DemoApi {

        okHttpClientBuilder
            .addInterceptor(appInfoInterceptor)

        return retrofitBuilder
            .client(okHttpClientBuilder.build())
            .build()
            .create(DemoApi::class.java)
    }

    companion object {
        private const val BASE_URL = BuildConfig.SERVER_URL
        private const val CONNECT_TIMEOUT = 45L
        private const val READ_TIMEOUT = 45L
        private const val WRITE_TIMEOUT = 45L
    }
}