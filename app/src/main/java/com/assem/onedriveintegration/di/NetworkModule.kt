package com.assem.onedriveintegration.di

import com.assem.onedriveintegration.data.remote.OneDriveApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLDecoder
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val token = "REPLACE_WITH_YOUR_TOKEN"

    @Provides
    @Singleton
    fun providesRetrofit(
        gsonConverterFactory: GsonConverterFactory, okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://graph.microsoft.com/v1.0/")
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()

    }

    @Provides
    @Singleton
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request()
                val newRequestBuilder = request.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                val url = request.url.toString()
                val decodedUrl = URLDecoder.decode(url, "UTF-8")
                chain.proceed(newRequestBuilder.url(decodedUrl).build())
            }

        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        val gsonBuilder = GsonBuilder().setLenient().create()
        return GsonConverterFactory.create(gsonBuilder)
    }

    @Provides
    internal fun provideLoginApi(retrofit: Retrofit): OneDriveApi {
        return retrofit.create(OneDriveApi::class.java)
    }
}