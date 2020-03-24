package com.delacrixmorgan.wishlist.android.ui.create

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.data.category.BudgetType
import kotlinx.android.synthetic.main.cell_budget_type_selectable.view.*

class BudgetTypeRecyclerViewAdapter(
    val listener: Listener
) : RecyclerView.Adapter<BudgetTypeRecyclerViewAdapter.BudgetTypeViewHolder>() {
    interface Listener {
        fun onBudgetTypeSelected(budgetType: BudgetType)
    }

    var currentSelection = 0
    var budgetType = BudgetType.values()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetTypeViewHolder {
        return BudgetTypeViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_budget_type_selectable, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BudgetTypeViewHolder, position: Int) {
        holder.bind(budgetType[position])
    }

    override fun getItemCount() = budgetType.size

    inner class BudgetTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(budgetType: BudgetType) = with(itemView) {
            val colorBlack = ContextCompat.getColor(context, android.R.color.black)
            val colorWhite = ContextCompat.getColor(context, android.R.color.white)
            val colorAccent = ContextCompat.getColor(context, R.color.colorAccent)

            if (currentSelection == budgetType.ordinal) {
                parentViewGroup.setBackgroundColor(colorAccent)
                nameTextView.setTextColor(colorWhite)
                selectedImageView.isVisible = true

            } else {
                parentViewGroup.setBackgroundColor(colorWhite)
                nameTextView.setTextColor(colorBlack)
                selectedImageView.isVisible = false
            }

            nameTextView.text = budgetType.name

            setOnClickListener {
                currentSelection = budgetType.ordinal
                listener.onBudgetTypeSelected(budgetType)
            }
        }
    }
}