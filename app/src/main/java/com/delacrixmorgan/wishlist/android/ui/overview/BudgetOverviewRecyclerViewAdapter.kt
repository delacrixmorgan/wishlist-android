package com.delacrixmorgan.wishlist.android.ui.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.common.moneyFormat
import com.delacrixmorgan.wishlist.android.data.model.Budget
import kotlinx.android.synthetic.main.cell_budget_overview_detail.view.*

class BudgetOverviewRecyclerViewAdapter(
    val overviewType: BudgetListFragment.OverviewType,
    val listener: Listener
) : RecyclerView.Adapter<BudgetOverviewRecyclerViewAdapter.BudgetViewHolder>() {
    interface Listener {
        fun onBudgetSelected(budget: Budget)
    }

    var items: MutableList<Budget> = mutableListOf()
        set(value) {
            val oldList = field
            val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return oldList[oldItemPosition].uuid == value[newItemPosition].uuid
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ) = oldList[oldItemPosition] == value[newItemPosition]

                override fun getOldListSize() = oldList.size

                override fun getNewListSize() = value.size

                override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int) = true
            }, true)

            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        return BudgetViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_budget_overview_detail, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class BudgetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(budget: Budget) = with(itemView) {
            titleTextView.text = budget.name
            amountTextView.text = when (overviewType) {
                BudgetListFragment.OverviewType.List -> budget.amountLeft.moneyFormat()
                BudgetListFragment.OverviewType.Spent -> budget.amountSpent.moneyFormat()
            }

            setOnClickListener {
                listener.onBudgetSelected(budget)
            }
        }
    }
}