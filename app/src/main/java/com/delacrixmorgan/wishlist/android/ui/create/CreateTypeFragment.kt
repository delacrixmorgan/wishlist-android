package com.delacrixmorgan.wishlist.android.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.data.category.BudgetType
import kotlinx.android.synthetic.main.fragment_create_type.*

class CreateTypeFragment : Fragment(), BudgetTypeRecyclerViewAdapter.Listener {

    companion object {
        fun create(listener: CreateListeners) = CreateTypeFragment().apply {
            this.listener = listener
        }
    }

    private lateinit var listener: CreateListeners
    private lateinit var adapter: BudgetTypeRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = BudgetTypeRecyclerViewAdapter(this)
        recyclerView.adapter = adapter
    }

    override fun onBudgetTypeSelected(budgetType: BudgetType) {
        adapter.notifyDataSetChanged()
        listener.onBudgetTypeSelected(budgetType)
    }
}