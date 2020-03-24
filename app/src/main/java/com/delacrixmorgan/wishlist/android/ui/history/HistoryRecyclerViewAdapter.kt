package com.delacrixmorgan.wishlist.android.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.common.formatDate
import com.delacrixmorgan.wishlist.android.common.moneyFormat
import com.delacrixmorgan.wishlist.android.data.model.Transaction
import kotlinx.android.synthetic.main.cell_history_detail.view.*

class HistoryRecyclerViewAdapter(val listener: Listener) :
    RecyclerView.Adapter<HistoryRecyclerViewAdapter.TransactionViewHolder>() {

    interface Listener {
        fun onTransactionSelected(transaction: Transaction)
    }

    var transactions: MutableList<Transaction> = mutableListOf()
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cell_history_detail, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount() = transactions.size

    inner class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(transaction: Transaction) = with(itemView) {
            descriptionTextView.text = transaction.name
            dateTextView.text = transaction.createdAt.formatDate()
            priceTextView.text = transaction.amountTransaction.moneyFormat()

            setOnClickListener {
                listener.onTransactionSelected(transaction)
            }
        }
    }
}