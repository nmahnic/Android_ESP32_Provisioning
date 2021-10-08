package com.nicomahnic.basic_esp_provisioning.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nicomahnic.basic_esp_provisioning.apis.apiESP.ApiHelper
import com.nicomahnic.basic_esp_provisioning.apis.apiESP.ApiHelperImpl
import com.nicomahnic.basic_esp_provisioning.apis.apiESP.ApiService
import com.nicomahnic.basic_esp_provisioning.apis.apiESP.ServerHelperImpl
import com.nicomahnic.basic_esp_provisioning.apis.apiServer.ServerHelper
import com.nicomahnic.basic_esp_provisioning.apis.apiServer.ServerService
import com.nicomahnic.basic_esp_provisioning.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .callTimeout(11, TimeUnit.SECONDS)
    }

    @Singleton
    @Provides
    @Named("ESP32")
    fun provideESPRetrofit(gson: Gson, okHttpClient: OkHttpClient.Builder): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideESPService(@Named("ESP32")retrofit: Retrofit): ApiService = retrofit.create(
        ApiService::class.java)

    @Provides
    @Singleton
    fun provideESPHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

    @Singleton
    @Provides
    @Named("Server")
    fun provideServerRetrofit(gson: Gson, okHttpClient: OkHttpClient.Builder): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.SERVER_URL)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideServerService(@Named("Server")retrofit: Retrofit): ServerService = retrofit.create(
        ServerService::class.java)

    @Provides
    @Singleton
    fun provideServerHelper(serverHelper: ServerHelperImpl): ServerHelper = serverHelper


}