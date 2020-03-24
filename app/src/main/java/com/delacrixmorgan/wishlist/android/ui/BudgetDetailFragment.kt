package com.delacrixmorgan.wishlist.android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.common.DateUtils
import com.delacrixmorgan.wishlist.android.common.moneyFormat
import com.delacrixmorgan.wishlist.android.data.category.BudgetType
import com.delacrixmorgan.wishlist.android.data.controller.BudgetDataController
import com.delacrixmorgan.wishlist.android.data.model.Budget
import com.delacrixmorgan.wishlist.android.data.model.Keys
import com.delacrixmorgan.wishlist.android.data.model.Transaction
import com.jem.liquidswipe.base.LiquidSwipeLayout
import com.jem.liquidswipe.clippathprovider.LiquidSwipeClipPathProvider
import kotlinx.android.synthetic.main.fragment_budget_detail.*
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class BudgetDetailFragment : Fragment() {

    companion object {
        fun create(
            budget: Budget,
            liquidSwipeClipPathProviders: LiquidSwipeClipPathProvider
        ) = BudgetDetailFragment().apply {
            arguments = bundleOf(Keys.BudgetDetail.BudgetId.name to budget.uuid)
            this.liquidSwipeClipPathProvider = liquidSwipeClipPathProviders
        }
    }

    private var budget: Budget? = null
    private lateinit var liquidSwipeClipPathProvider: LiquidSwipeClipPathProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_budget_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)
        (parentViewGroup as? LiquidSwipeLayout)?.clipPathProvider = liquidSwipeClipPathProvider

        val budgetUuid = arguments?.getString(Keys.BudgetDetail.BudgetId.name) ?: ""
        budget = BudgetDataController.getBudgetById(budgetUuid)

        amountTextView.text = budget?.amountLeft?.moneyFormat()
        daysTextView.text = "${DateUtils.daysLeft}"

        progressBar.progress = DateUtils.currentDay
        progressBar.max = DateUtils.lastDay

        when (budget?.type) {
            BudgetType.General -> {
                parentViewGroup.setBackgroundColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.colorGreen
                    )
                )
            }
            BudgetType.Food -> {
                parentViewGroup.setBackgroundColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.colorPurple
                    )
                )
            }
            BudgetType.Transport -> {
                parentViewGroup.setBackgroundColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.colorBlue
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onTransaction(transaction: Transaction) {
        if (transaction.budgetUuid == budget?.uuid) {
            budget?.let {
                amountTextView.text = it.amountLeft.moneyFormat()
                lifecycleScope.launch { BudgetDataController.updateBudget(it) }
            }
        }
    }
}