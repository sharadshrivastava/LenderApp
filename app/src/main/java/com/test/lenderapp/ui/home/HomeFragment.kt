package com.test.lenderapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.test.lenderapp.R
import com.test.lenderapp.data.Resource
import com.test.lenderapp.data.model.AccountsItem
import com.test.lenderapp.databinding.HomeFragmentBinding

class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var vm: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    //private var adapter: TransactionsAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        vm = ViewModelProvider(this).get(HomeViewModel::class.java)
        observeAccountLiveData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.swiperefresh.setOnRefreshListener(this)
        setupTransactionsList(null)
    }

    private fun setupTransactionsList(list: MutableList<AccountsItem?>?) {
//        if (adapter == null) {
//            binding.isLoading = true
//            adapter = TransactionsAdapter(listener, list)
//        }
//        setupListView(binding.transactionsList, adapter)
//        binding.vm = vm
    }

    private fun setupListView(listView: RecyclerView?/*, adapter: TransactionsAdapter?*/) {
        listView?.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(context)
        listView?.layoutManager = mLayoutManager
        //listView?.adapter = adapter
        //listView?.addItemDecoration(VerticalSpaceItemDecoration(Utils.pxFromDp(context, 1f).toInt()))
    }

    private fun observeAccountLiveData() {
        vm.getAccountDetails().observe(this, Observer { resource ->
            if (resource?.status == Resource.Status.SUCCESS) {
                binding.isLoading = false
                binding.swiperefresh.isRefreshing = false

                //vm.setData(resource.data)
                //setAtmInfo(vm.getAtms())
                //refreshTransactions(vm.getAllTransactions())
            } else {
                binding.isLoading = false
                Snackbar.make(binding.root, resource?.message ?: "Error", Snackbar.LENGTH_LONG).show()
            }
        })
    }

    override fun onRefresh() {
        observeAccountLiveData()
    }

    /*private fun refreshTransactions(list: List<BaseTransactionItem?>?) {
        adapter?.setData(list)
    }*/

    companion object {
        fun newInstance() = HomeFragment()
    }

}
