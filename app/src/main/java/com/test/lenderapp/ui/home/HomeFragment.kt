package com.test.lenderapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
import com.test.lenderapp.ui.components.CirclePagerIndicatorDecoration
import com.test.lenderapp.util.Utils
import kotlinx.android.synthetic.main.main_activity.*


class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var vm: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private var adapter: AccountsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true);
        vm = ViewModelProvider(this).get(HomeViewModel::class.java)
        observeAccountLiveData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.swipeRefresh.setOnRefreshListener(this)
        setupMonths()
        setupAccountsList(null)
    }

    private fun setupMonths() {
        activity?.toolbar?.findViewById<Spinner>(R.id.spinner)?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    //months selection actions will go here.
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun setupAccountsList(list: MutableList<AccountsItem?>?) {
        if (adapter == null) {
            binding.isLoading = true
            adapter = AccountsAdapter(list)
        }
        setupListView(binding.accountsList, adapter)
        binding.vm = vm
    }

    private fun setupListView(listView: RecyclerView?, adapter: AccountsAdapter?) {
        listView?.setHasFixedSize(true)
        listView?.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        listView?.adapter = adapter
        listView?.addItemDecoration(CirclePagerIndicatorDecoration(listView.context))
    }

    private fun observeAccountLiveData() {
        vm.getAccountDetails().observe(this, Observer { resource ->
            if (resource?.status == Resource.Status.SUCCESS) {
                binding.isLoading = false
                binding.swipeRefresh.isRefreshing = false

                vm.setData(resource.data)
                refreshAccounts(resource.data?.accounts)
            } else {
                binding.isLoading = false
                Snackbar.make(binding.root, resource?.message ?: "Error", Snackbar.LENGTH_LONG)
                    .show()
            }
        })
    }

    override fun onRefresh() {
        observeAccountLiveData()
    }

    private fun refreshAccounts(list: List<AccountsItem?>?) {
        adapter?.setData(list)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}