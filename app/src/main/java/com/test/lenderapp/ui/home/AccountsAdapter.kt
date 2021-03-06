package com.test.lenderapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.lenderapp.LenderApp
import com.test.lenderapp.R
import com.test.lenderapp.data.model.AccountsItem
import com.test.lenderapp.data.model.MonthsItem
import com.test.lenderapp.databinding.LayoutAccountsRowBinding
import com.test.lenderapp.util.Utils
import kotlinx.android.synthetic.main.layout_vertical_bars.view.*
import kotlinx.android.synthetic.main.layout_vertical_chart.view.*


class AccountsAdapter(
    private var accountsList: MutableList<AccountsItem?>?
) :
    RecyclerView.Adapter<AccountsAdapter.AccountsViewHolder>() {

    private lateinit var context: Context
    private lateinit var binding: LayoutAccountsRowBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsViewHolder {
        binding = DataBindingUtil.inflate<LayoutAccountsRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.layout_accounts_row, parent, false
        )
        context = parent.context
        return AccountsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {
        holder.bindData(accountsList?.get(position))
    }

    override fun getItemCount(): Int {
        return accountsList.orEmpty().size
    }

    inner class AccountsViewHolder(private var binding: LayoutAccountsRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(account: AccountsItem?) {
            manageAccountsRow(account)
            manageVerticalCharts(account)
        }

        private fun manageAccountsRow(account: AccountsItem?) {
            var drawable = 0
            when (account?.name) {
                "ANZ" -> drawable = R.drawable.card_bg_blue
                "CommBank" -> drawable = R.drawable.card_bg_green
                "Westpac" -> drawable = R.drawable.card_bg_red
            }
            binding.cardLayout.setBackgroundResource(drawable)
        }

        private fun manageVerticalCharts(account: AccountsItem?) {
            setChartValues(
                binding.verticalBarsLayout.firstMonthChart,
                account?.months?.get(Months.Jan.pos)
            )
            setChartValues(
                binding.verticalBarsLayout.secondMonthChart,
                account?.months?.get(Months.Feb.pos)
            )
            setChartValues(
                binding.verticalBarsLayout.thirdMonthChart,
                account?.months?.get(Months.March.pos)
            )
            setChartValues(
                binding.verticalBarsLayout.fourthMonthChart,
                account?.months?.get(Months.April.pos)
            )
        }

        private fun setChartValues(layout: View, monthItem: MonthsItem?) {
            layout.totalAmount.text =
                context.getString(R.string.expense, monthItem?.total.toString())
            layout.month.text = monthItem?.name
            layout.verticalChart.layoutParams = getLayoutParams(getChartHeight(monthItem?.total))
        }

        private fun getChartHeight(total: Double?): Float {
            val heightRatio = 3
            return (if (total == null) 0.0 else total / heightRatio).toFloat()
        }
    }

    fun setData(transactions: List<AccountsItem?>?) {
        if (accountsList != null) {
            accountsList?.clear()
            accountsList?.let {list->transactions?.let(list::addAll) }
        } else {
            accountsList = transactions?.toMutableList()
        }
        notifyDataSetChanged()
    }

    fun getLayoutParams(height: Float): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(
            LenderApp.get().resources.getDimension(R.dimen.bar_width).toInt(),
            Utils.DpFromPx(context, height).toInt()
        )
    }

    enum class Months(val pos: Int) {
        Jan(0),
        Feb(1),
        March(2),
        April(3)
    }
}