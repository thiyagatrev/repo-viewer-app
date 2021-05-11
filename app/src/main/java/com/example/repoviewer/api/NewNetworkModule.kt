package com.example.repoviewer.api

import android.util.Log
import com.example.repoviewer.BuildConfig
import com.example.repoviewer.utils.Constants
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NewNetworkModule {
    const val AUTH_API = "auth"
    const val OPEN_API = "open"

    fun providesBaseUrl(): String = Constants.getBaseUrl()

    fun providesRetrofitBuilder(): Retrofit.Builder = Retrofit.Builder()

    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    fun providesGson(): Gson = GsonBuilder().create()


    fun providesNetworkInterceptor(): StethoInterceptor = StethoInterceptor()
    fun providesOkHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()
    fun providesOkhttpClientWithAuthentication(
        okhttpBuilder: OkHttpClient.Builder,
        stethoInterceptor: StethoInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        if (BuildConfig.DEBUG) {
            okhttpBuilder.addInterceptor(loggingInterceptor)
        }
        return okhttpBuilder.addInterceptor(stethoInterceptor)
            .addInterceptor { chain ->
                val original: Request = chain.request()
                val request: Request = original.newBuilder()
                    .header("Content-Type", "application/json; charset=utf-8")
                    .build()

                val response: Response = chain.proceed(request)
                when (response.code) {
                    401 -> {
                        Log.e("Unauthorized-->", "$response")
                    }
                    400, 403, 404 -> {
                        Log.e("Error--> ", "$response")
                    }
                    500, 503 -> {
                        Log.e("ServerError-->", "$response")
                    }
                }
                response
            }
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .followRedirects(false).build()
    }

    fun providesOkhttpClient(
        okhttpBuilder: OkHttpClient.Builder,
        stethoInterceptor: StethoInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        if (BuildConfig.DEBUG) {
            okhttpBuilder.addInterceptor(loggingInterceptor)
        }
        return okhttpBuilder
            .addInterceptor(stethoInterceptor)
            .addInterceptor { chain ->
                val original: Request = chain.request()
                val request: Request = original.newBuilder()
                    .header("Content-Type", "application/json; charset=utf-8")
                    .build()

                val response: Response = chain.proceed(request)
                when (response.code) {
                    401 -> {
                        Log.e("Unauthorized-->", response.message)
                    }
                    400, 403, 404 -> {
                        Log.e("Error--> ", response.message)
                    }
                    500, 503 -> {
                        Log.e("ServerError-->", response.message)
                    }
                }
                response
            }
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .followRedirects(false).build()
    }


    fun providesRetrofitWithoutAuthentication(
        baseUrl: String,
        okhttpClient: OkHttpClient, gson: Gson,
        retrofitBuilder: Retrofit.Builder
    ): Retrofit {
        return retrofitBuilder
            .baseUrl(baseUrl)
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    fun providesRApiCallsWithAuthentication(retrofit: Retrofit): ApiCalls {
        return retrofit.create(ApiCalls::class.java)
    }

    fun providesApiCallsWithoutAuthentication(retrofit: Retrofit): ApiCalls {
        return retrofit.create(ApiCalls::class.java)
    }

}