package com.test.lenderapp.data

import com.test.lenderapp.data.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface AccountDetailsApi {

    @GET(ACCT_DETAILS_PATH)
    fun getAcctDetails(): Call<ApiResponse>

    companion object {
        const val BASE_URL = "https://www.cheq.com"
        const val ACCT_DETAILS_PATH = "api/v1/accounts"
    }
}