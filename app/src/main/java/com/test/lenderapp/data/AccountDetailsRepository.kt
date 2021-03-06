package com.test.lenderapp.data

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
}