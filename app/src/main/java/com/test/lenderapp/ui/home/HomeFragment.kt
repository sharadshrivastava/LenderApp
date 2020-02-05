package com.test.lenderapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
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

    private var posLiveData =  MutableLiveData<Int>();
    private var currPos:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true);
        vm = ViewModelProvider(this).get(HomeViewModel::class.java)
        observeAccountLiveData()
        observePosLiveData()
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
                    parent: AdapterView<*>?, view: View?, monthPos: Int, id: Long) {
                    setupExpenseCharts(currPos, monthPos)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun setupAccountsList(list: MutableList<AccountsItem?>?) {
        if (adapter == null) {
            binding.isLoading = true
            adapter = AccountsAdapter(this, list)
        }
        setupListView(binding.accountsList, adapter)
        binding.vm = vm
    }

    private fun setupListView(listView: RecyclerView?, adapter: AccountsAdapter?) {
        listView?.setHasFixedSize(true)
        listView?.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        listView?.adapter = adapter
        listView?.addItemDecoration(CirclePagerIndicatorDecoration(listView.context,posLiveData))
        PagerSnapHelper().attachToRecyclerView(listView)
    }

    private fun observeAccountLiveData() {
        vm.getAccountDetails().observe(this, Observer { resource ->
            if (resource?.status == Resource.Status.SUCCESS) {
                binding.isLoading = false
                binding.swipeRefresh.isRefreshing = false

                vm.setData(resource.data)
                refreshAccounts(resource.data?.accounts)
                setupExpenseCharts(0,0)
            } else {
                binding.isLoading = false
                Snackbar.make(binding.root, resource?.message ?: "Error", Snackbar.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun observePosLiveData(){
        posLiveData.observe(this, Observer { pos ->
            if(currPos!=pos) {
                currPos = pos
                setupExpenseCharts(pos, 0)
            }
        })
    }

    override fun onRefresh() {
        observeAccountLiveData()
    }

    private fun refreshAccounts(list: List<AccountsItem?>?) {
        adapter?.setData(list)
    }

    private fun setupExpenseCharts(accountPos:Int, monthPos:Int){
        setupExpenseCharts(vm.response?.accounts?.get(accountPos)?.months?.get(monthPos))
    }

    private fun setupExpenseCharts(month:MonthsItem?){
        setupExpenses(houseHold, R.drawable.home, getString(R.string.household),
            month?.household, month?.total)
        setupExpenses(grocery, R.drawable.basket, getString(R.string.grocery),
            month?.groceries, month?.total)
        setupExpenses(transport, R.drawable.bus, getString(R.string.transport),
            month?.transport, month?.total)
        setupExpenses(food, R.drawable.cherry, getString(R.string.food),
            month?.food, month?.total)
    }

    private fun setupExpenses(view: View, icon:Int, name:String, household:Double?, total:Double?){
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