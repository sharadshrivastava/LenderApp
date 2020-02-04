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
        setupHousehold(month?.household, month?.total)
        setupGrocery(month?.groceries, month?.total)
        setupTransport(month?.transport, month?.total)
        setupFood(month?.food, month?.total)
    }

    private fun setupHousehold(household:Double?, total:Double?){
        houseHold.chartIcon.setImageResource(R.drawable.home)
        houseHold.chartName.text = getString(R.string.household)
        houseHold.amount.text = getString(R.string.expense,household?.toString())
        val value:Int = vm.getExpensePercent(household?:0.0, total?:0.0).toInt()
        houseHold.percent.text = getString(R.string.percentValue, value,getString(R.string.percent) )
        houseHold.circularChart.progress = value
    }

    private fun setupGrocery(groceries:Double?, total:Double?){
        grocery.chartIcon.setImageResource(R.drawable.basket)
        grocery.chartName.text = getString(R.string.grocery)
        grocery.amount.text = getString(R.string.expense, groceries.toString())
        val value:Int = vm.getExpensePercent(groceries?:0.0, total?:0.0).toInt()
        grocery.percent.text = getString(R.string.percentValue, value ,getString(R.string.percent))
        grocery.circularChart.progress = value
    }

    private fun setupTransport(transports:Double?, total:Double?){
        transport.chartIcon.setImageResource(R.drawable.bus)
        transport.chartName.text = getString(R.string.transport)
        transport.amount.text = getString(R.string.expense, transports.toString())
        val value:Int = vm.getExpensePercent(transports?:0.0, total?:0.0).toInt()
        transport.percent.text = getString(R.string.percentValue, value,getString(R.string.percent))
        transport.circularChart.progress = value
    }

    private fun setupFood(foods:Double?, total:Double?){
        food.chartIcon.setImageResource(R.drawable.cherry)
        food.chartName.text = getString(R.string.food)
        food.amount.text = getString(R.string.expense, foods.toString())
        val value:Int = vm.getExpensePercent(foods?:0.0, total?:0.0).toInt()
        food.percent.text = getString(R.string.percentValue,value,getString(R.string.percent))
        food.circularChart.progress = value
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}