package com.test.lenderapp.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.test.lenderapp.LenderApp
import com.test.lenderapp.data.AccountDetailsRepository
import com.test.lenderapp.data.Resource
import com.test.lenderapp.data.model.ApiResponse
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var accountDetailsRepository: AccountDetailsRepository

    var response: ApiResponse? = null

    init {
        LenderApp.get().component.inject(this)
    }

    fun setData(responseData: ApiResponse?) {
        response = responseData
    }

    fun getAccountDetails(): LiveData<Resource<ApiResponse>> = liveData(Dispatchers.IO){
        val response = accountDetailsRepository.getAccountDetails()
        emit(response)
    }

    fun getExpensePercent(current: Double, total:Double):Double = (current/total)*100
}
