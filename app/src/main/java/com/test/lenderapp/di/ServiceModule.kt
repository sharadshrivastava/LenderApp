package com.test.lenderapp.di

import com.test.lenderapp.data.AccountDetailsApi
import com.test.lenderapp.util.MockInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class ServiceModule {

    @Provides
    @Singleton
    fun retrofit(client: OkHttpClient):Retrofit = Retrofit.Builder()
        .baseUrl(AccountDetailsApi.BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()


    @Provides
    @Singleton
    fun tramApi(retrofit: Retrofit): AccountDetailsApi = retrofit.create(AccountDetailsApi::class.java);

    @Provides
    @Singleton
    fun getClient(): OkHttpClient {
        val interceptor =  HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(interceptor)
            .addInterceptor(MockInterceptor()) //This is for offline data testing.
            .build()
    }

}