package com.test.lenderapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.lenderapp.R
import com.test.lenderapp.data.model.AccountsItem
import com.test.lenderapp.databinding.LayoutAccountsRowBinding


class AccountsAdapter(private var accountsList: MutableList<AccountsItem?>?) :
    RecyclerView.Adapter<AccountsAdapter.AccountsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsViewHolder {
        val binding = DataBindingUtil.inflate<LayoutAccountsRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.layout_accounts_row, parent, false
        )
        return AccountsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {
        val prevTransaction = if (position == 0) null else accountsList?.get(position - 1)
        holder.bindData(prevTransaction, accountsList?.get(position))
    }

    override fun getItemCount(): Int {
        return if (accountsList == null) 0 else accountsList!!.size
    }

    inner class AccountsViewHolder(private var binding: LayoutAccountsRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(prevTransaction: AccountsItem?, transaction: AccountsItem?) {
            manageAccountsRow(transaction)
        }

        private fun manageAccountsRow(account: AccountsItem?) {
//            binding.description.text =
//                    if (transaction is PendingItem) getPendingText(transaction.description)
//                    else transaction?.description
//
//            binding.balance.text = itemView.context.getString(R.string.currency, transaction?.amount.toString())
            var drawable = 0
            when (account?.name) {
                "ANZ" -> drawable = R.drawable.card_bg_blue
                "CommBank" -> drawable = R.drawable.card_bg_green
                "Westpac"  -> drawable = R.drawable.card_bg_red
            }
            binding.cardLayout.setBackgroundResource(drawable)
        }
    }

    fun setData(transactions: List<AccountsItem?>?) {
        if (accountsList != null) {
            accountsList!!.clear()
            accountsList!!.addAll(transactions!!.toList())
        } else {
            accountsList = transactions?.toMutableList()
        }
        notifyDataSetChanged()
    }
}