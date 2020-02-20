package com.test.lenderapp.data

import androidx.lifecycle.MutableLiveData
import com.test.lenderapp.data.model.ApiResponse

import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountDetailsRepository @Inject constructor() {

    private val TAG = AccountDetailsRepository::class.java.name

    @Inject
    lateinit var accountDetailsApi: AccountDetailsApi

    suspend fun getAccountDetails():Resource<ApiResponse>{
        try{
            return ResponseHandler.handleSuccess(accountDetailsApi.getAcctDetails())
        }catch (e: Exception){
            return ResponseHandler.handleException(e)
        }
    }

    private fun handleFailure(liveData: MutableLiveData<*>, msg: String) {
        liveData.value = Resource.error<Any>(msg, null)
    }
}