package com.delacrixmorgan.wishlist.android.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.common.bottomsheet.ActionBottomSheetFragment
import com.delacrixmorgan.wishlist.android.common.bottomsheet.ConfirmationBottomSheetFragment
import com.delacrixmorgan.wishlist.android.common.bottomsheet.EditBottomSheetFragment
import com.delacrixmorgan.wishlist.android.data.category.DateType
import com.delacrixmorgan.wishlist.android.data.controller.BudgetDataController
import com.delacrixmorgan.wishlist.android.data.controller.TransactionDataController
import com.delacrixmorgan.wishlist.android.data.model.Keys
import com.delacrixmorgan.wishlist.android.data.model.SortType
import com.delacrixmorgan.wishlist.android.data.model.Transaction
import kotlinx.android.synthetic.main.fragment_history_detail.*

class HistoryDetailFragment : Fragment(),
    HistoryRecyclerViewAdapter.Listener,
    ActionBottomSheetFragment.Listener,
    EditBottomSheetFragment.Listener,
    ConfirmationBottomSheetFragment.Listener {

    companion object {
        fun create(budgetId: String, dateType: DateType) = HistoryDetailFragment().apply {
            arguments = bundleOf(
                Keys.History.BudgetId.name to budgetId,
                Keys.History.DateType.name to dateType
            )
        }
    }

    private lateinit var viewModel: HistoryDetailViewModel
    private lateinit var adapter: HistoryRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)
            .get(HistoryDetailViewModel::class.java)

        val budget = BudgetDataController.getBudgetById(
            requireNotNull(
                arguments?.getString(Keys.History.BudgetId.name)
            )
        )

        val dateType = requireNotNull(
            arguments?.getSerializable(Keys.History.DateType.name) as DateType
        )

        adapter = HistoryRecyclerViewAdapter(this)
        adapter.transactions = TransactionDataController.getTransaction(
            budget, sortType = SortType.DateDescending
        ).toMutableList()

        recyclerView.adapter = adapter
        emptyStateViewGroup.isVisible = adapter.transactions.isEmpty()

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            adapter.transactions = TransactionDataController.getTransaction(
                budget, sortType = SortType.DateDescending
            ).toMutableList()
        }
    }

    private fun showEditBottomSheet(title: String?) {
        EditBottomSheetFragment.create(this, title).show(
            childFragmentManager,
            ActionBottomSheetFragment::class.simpleName
        )
    }

    private fun showDeleteConfirmationBottomSheet() {
        ConfirmationBottomSheetFragment.create(this, "Are you sure you want to delete?").show(
            childFragmentManager,
            ActionBottomSheetFragment::class.simpleName
        )
    }

    override fun onTransactionSelected(transaction: Transaction) {
        viewModel.transaction = transaction
        val actionBottomSheetFragment = ActionBottomSheetFragment.create(
            ActionBottomSheetFragment.ActionType.Transaction, this
        )
        actionBottomSheetFragment.show(
            childFragmentManager,
            ActionBottomSheetFragment::class.simpleName
        )
    }

    override fun onItemEdited() {
        showEditBottomSheet(viewModel.transaction?.name)
    }

    override fun onItemDeleted() {
        showDeleteConfirmationBottomSheet()
    }

    override fun onEditSubmit(title: String) {
        // TODO Edit Transaction
    }

    override fun onConfirmSubmit() {
        // TODO Delete Transaction
    }
}