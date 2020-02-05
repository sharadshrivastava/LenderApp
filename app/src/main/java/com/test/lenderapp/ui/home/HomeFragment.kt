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
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.test.lenderapp.R
import com.test.lenderapp.data.Resource
import com.test.lenderapp.data.model.AccountsItem
import com.test.lenderapp.data.model.MonthsItem
import com.test.lenderapp.databinding.HomeFragmentBinding
import com.test.lenderapp.ui.components.CirclePagerIndicatorDecoration
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.layout_circular_chart.view.*
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
        PagerSnapHelper().attachToRecyclerView(listView)
    }

    private fun observeAccountLiveData() {
        vm.getAccountDetails().observe(this, Observer { resource ->
            if (resource?.status == Resource.Status.SUCCESS) {
                binding.isLoading = false
                binding.swipeRefresh.isRefreshing = false

                refreshAccounts(resource.data?.accounts)
                setupExpenseCharts(resource.data?.accounts?.get(0)?.months?.get(0))
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

    private fun setupExpenseCharts(month:MonthsItem?){
        setupHousehold(houseHold, R.drawable.home, getString(R.string.household),
            month?.household, month?.total)
        setupHousehold(grocery, R.drawable.basket, getString(R.string.grocery),
            month?.groceries, month?.total)
        setupHousehold(transport, R.drawable.bus, getString(R.string.transport),
            month?.transport, month?.total)
        setupHousehold(food, R.drawable.cherry, getString(R.string.food),
            month?.food, month?.total)
    }

    private fun setupHousehold(view: View, icon:Int, name:String, household:Double?, total:Double?){
        view.chartIcon.setImageResource(icon)
        view.chartName.text = name
        view.amount.text = getString(R.string.expense,household?.toString())
        val expenses = vm.getExpensePercent(household?:0.0, total?:0.0).toInt()
        view.percent.text = getString(R.string.percentValue, expenses,getString(R.string.percent) )
        view.circularChart.progress = expenses
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}