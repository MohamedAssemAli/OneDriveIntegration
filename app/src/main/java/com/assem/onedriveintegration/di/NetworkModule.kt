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

    private val token =
        "eyJ0eXAiOiJKV1QiLCJub25jZSI6Ik11YzlFcGY0TlFpd0xyU0Z2YURmMy0tdVAxNEZ5bXYwa3ZKcVV2R1RQX0kiLCJhbGciOiJSUzI1NiIsIng1dCI6IkpETmFfNGk0cjdGZ2lnTDNzSElsSTN4Vi1JVSIsImtpZCI6IkpETmFfNGk0cjdGZ2lnTDNzSElsSTN4Vi1JVSJ9.eyJhdWQiOiIwMDAwMDAwMy0wMDAwLTAwMDAtYzAwMC0wMDAwMDAwMDAwMDAiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC80M2Q5ZjQ5Ny1kZjUyLTRkMzgtYWIyNC1lZThmM2ViNGZhNDQvIiwiaWF0IjoxNzQyMTgwNDIwLCJuYmYiOjE3NDIxODA0MjAsImV4cCI6MTc0MjI2NzEyMCwiYWNjdCI6MCwiYWNyIjoiMSIsImFjcnMiOlsicDEiXSwiYWlvIjoiQVpRQWEvOFpBQUFBbHFjOFFGaHpUMmI2VWxZK1FXWGpHOGw4azJpUGFOSDNOS1FmeCtCNXpDWmRueG80bC9WYTRFQ04wOE5wSWUrQWFXek1jTHUyY0ZqYldpTEx6bTNKeXpPNEpoUnJSUkx5YUpDR1lsVDFuUE9DSW5hdHk4TEVFczJQeHpKam1GS3pqejdkblRFM3V4UXFCTXRZS3dwQlQrZzR5MW9ZWDhiSUdGNFBaSjNjMGYvL1NGaVgrbmtCMmhOMlBiWmcxUllZIiwiYW1yIjpbInJzYSIsIm1mYSJdLCJhcHBfZGlzcGxheW5hbWUiOiJHcmFwaCBFeHBsb3JlciIsImFwcGlkIjoiZGU4YmM4YjUtZDlmOS00OGIxLWE4YWQtYjc0OGRhNzI1MDY0IiwiYXBwaWRhY3IiOiIwIiwiZGV2aWNlaWQiOiJlODhkODRlMy1hZWUxLTRlYzUtODViNi0zMGVjMzllMWVlZTkiLCJmYW1pbHlfbmFtZSI6IkFzc2VtIiwiZ2l2ZW5fbmFtZSI6Ik1vaGFtZWQiLCJpZHR5cCI6InVzZXIiLCJpcGFkZHIiOiIxNTYuMjE0LjIzLjE2NSIsIm5hbWUiOiJNb2hhbWVkIEFzc2VtIiwib2lkIjoiMWM5ZmZiNWUtOTFjMi00OWRjLTlmOWMtN2UxZWJiNDc0YzFkIiwicGxhdGYiOiIzIiwicHVpZCI6IjEwMDMyMDAxNjY5QjYzMTgiLCJyaCI6IjEuQVRFQWxfVFpRMUxmT0UyckpPNlBQclQ2UkFNQUFBQUFBQUFBd0FBQUFBQUFBQURqQURJeEFBLiIsInNjcCI6IkZpbGVzLlJlYWQgb3BlbmlkIHByb2ZpbGUgVXNlci5SZWFkIGVtYWlsIiwic2lkIjoiMDAyMjM1ZTktZTgyMS0zNTNkLTQ1MjgtNTgwMWI4NzE5N2Y4Iiwic2lnbmluX3N0YXRlIjpbImttc2kiXSwic3ViIjoiT05ld0lKZU94V0VIdnVxLWJWdjNGenQ2NGVKTWhWR2lhY19jSWlCRnA2WSIsInRlbmFudF9yZWdpb25fc2NvcGUiOiJFVSIsInRpZCI6IjQzZDlmNDk3LWRmNTItNGQzOC1hYjI0LWVlOGYzZWI0ZmE0NCIsInVuaXF1ZV9uYW1lIjoibWFzc2VtQHlvdXhlbC5jb20iLCJ1cG4iOiJtYXNzZW1AeW91eGVsLmNvbSIsInV0aSI6IjhjUzluYVhYUVVlN1VUeDc5NVl4QUEiLCJ2ZXIiOiIxLjAiLCJ3aWRzIjpbImI3OWZiZjRkLTNlZjktNDY4OS04MTQzLTc2YjE5NGU4NTUwOSJdLCJ4bXNfY2MiOlsiQ1AxIl0sInhtc19mdGQiOiJiVFZ2cHhLeDRjMWVtVmxUVkNvMVRoQXRBRzZlTEhSLXVGM0gtRGxVa2xBIiwieG1zX2lkcmVsIjoiMSA4IiwieG1zX3NzbSI6IjEiLCJ4bXNfc3QiOnsic3ViIjoidmtmdzBuNmhoaWtjeElHd3JqNVdJYU5BZmVycjhYeXFwNVZjaGNvSUdKNCJ9LCJ4bXNfdGNkdCI6MTUzNTkwMDkzN30.Xh_CdTKE_59chpeeYvsetRbhANffmlpKzZY4Z4xgj_Z3asaQSMD8aboy8KFWHrA5QMTiqp8YxOzD6QNL8nfCq7Uhx12RvIaumfuangGJns4C3Kfn53H-yYsq9XL68c8P-jKjP7GbOK24ysXaLpFOh4UPEl-Z2fka2Ne8mAmwKk9F41KPs2IkE66oTpN1WS12yIntDV9JM1kgF12UMl9gLW7q08a_BOGluKN-2kNOvayq2k1g0UKFxHlI3zjtxjoBCvkp2_bW1lhP8Mo9EykJWTJKhnQU1CzPudfWcKSS5Io5O47aZ5d6j7M6kx3VMMGdJPCega5ymeklYKhlTuhmTA"

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