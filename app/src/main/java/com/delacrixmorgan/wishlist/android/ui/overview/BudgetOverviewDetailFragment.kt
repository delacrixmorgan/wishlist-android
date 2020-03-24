package com.delacrixmorgan.wishlist.android.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.data.controller.BudgetDataController
import com.delacrixmorgan.wishlist.android.data.model.Budget
import com.delacrixmorgan.wishlist.android.data.model.BudgetRefreshEvent
import com.delacrixmorgan.wishlist.android.data.model.Keys
import kotlinx.android.synthetic.main.fragment_budget_overview_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class BudgetOverviewDetailFragment : Fragment(), BudgetOverviewRecyclerViewAdapter.Listener {

    companion object {
        fun create(
            type: BudgetListFragment.OverviewType,
            listener: Listener
        ) = BudgetOverviewDetailFragment().apply {
            arguments = bundleOf(Keys.BudgetOverview.OverviewType.name to type)
            this.listener = listener
        }
    }

    private lateinit var adapter: BudgetOverviewRecyclerViewAdapter

    interface Listener {
        fun onBudgetSelected(budget: Budget)
    }

    private lateinit var listener: Listener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_budget_overview_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)

        val overviewType = requireNotNull(
            arguments?.getSerializable(
                Keys.BudgetOverview.OverviewType.name
            ) as BudgetListFragment.OverviewType
        )

        adapter = BudgetOverviewRecyclerViewAdapter(overviewType, this)
        adapter.items = BudgetDataController.budgets

        recyclerView.adapter = adapter
        emptyStateViewGroup.isVisible = adapter.items.isEmpty()

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onBudget(budget: Budget) {
        adapter.notifyItemInserted(adapter.items.size)
    }

    @Subscribe
    fun onBudgetRefreshEvent(budgetRefreshEvent: BudgetRefreshEvent) {
        adapter.notifyDataSetChanged()
    }

    override fun onBudgetSelected(budget: Budget) {
        listener.onBudgetSelected(budget)
    }
}