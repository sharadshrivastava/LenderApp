package com.test.lenderapp.util

import com.test.lenderapp.LenderApp
import com.test.lenderapp.data.AccountDetailsApi.Companion.ACCT_DETAILS_PATH
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody

// This class is just to support offline JSON with Retrofit for testing.
// In real app this class won't be required.
class MockInterceptor:Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val path  = chain.request().url.toUri().path
        var response = ""
        if(path == "/$ACCT_DETAILS_PATH"){
            response = getResponseData()
        }
        return getResponse(chain.request(), response)
    }

    private fun getResponse(request: Request,response:String):Response{
        return Response.Builder().code(200).message(response).request(request)
            .protocol(Protocol.HTTP_1_0)
            .body(response.toResponseBody())
            .addHeader("content-type", "application/json")
            .build()
    }

    private fun getResponseData():String{
        val input = LenderApp.get().assets.open("data.json")
        return input.bufferedReader().use { it.readText() }
    }
}