package com.test.lenderapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.lenderapp.LenderApp
import com.test.lenderapp.R
import com.test.lenderapp.data.model.AccountsItem
import com.test.lenderapp.data.model.ApiResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountDetailsRepository @Inject constructor() {

    private val TAG = AccountDetailsRepository::class.java.name

    @Inject
    lateinit var accountDetailsApi: AccountDetailsApi

    fun getAccountDetails(): LiveData<Resource<ApiResponse>> {
        val data = MutableLiveData<Resource<ApiResponse>>()

        accountDetailsApi.getAcctDetails().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    data.setValue(Resource.success(apiResponse))
                } else {
                    handleFailure(data, response.message())
                    response.errorBody()?.close()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e(TAG, t.stackTrace.toString())
                handleFailure(data, LenderApp.get().getString(R.string.network_error))
            }
        })
        return data
    }

    private fun handleFailure(liveData: MutableLiveData<*>, msg: String) {
        liveData.value = Resource.error<Any>(msg, null)
    }
}