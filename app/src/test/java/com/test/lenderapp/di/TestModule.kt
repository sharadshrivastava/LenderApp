package com.test.lenderapp.di

import com.test.lenderapp.data.AccountDetailsApi
import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class TestModule {

    @Provides
    @Singleton
    fun mockServer(): MockWebServer = MockWebServer()

    @Provides
    @Singleton
    fun retrofit(mockWebServer: MockWebServer): Retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("").toString())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun accountDetailsApi(retrofit: Retrofit): AccountDetailsApi = retrofit.create(AccountDetailsApi::class.java)


}