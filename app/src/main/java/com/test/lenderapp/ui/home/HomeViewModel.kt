package com.test.lenderapp.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.test.lenderapp.LenderApp
import com.test.lenderapp.data.AccountDetailsRepository
import com.test.lenderapp.data.Resource
import com.test.lenderapp.data.model.AccountsItem
import com.test.lenderapp.data.model.ApiResponse
import javax.inject.Inject

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var accountDetailsRepository: AccountDetailsRepository

    private var data: ApiResponse? = null

    init {
        LenderApp.get().component.inject(this)
    }

    fun getAccountDetails(): LiveData<Resource<ApiResponse>> = accountDetailsRepository.getAccountDetails()

    fun setData(response: ApiResponse?) {
        data = response
        //accountObservableField.set(data?.account)
    }

    /*fun getAllTransactions(): List<BaseTransactionItem?>? {
        var allTransactions = data?.transactions?.asSequence()?.plus(data?.pending.orEmpty())
        allTransactions = allTransactions?.sortedByDescending { Utils.getDate(it?.effectiveDate) }
        return allTransactions?.toList()
    }

    fun getAtms(): List<AtmItem?>? {
        return data?.atms
    }*/

}
