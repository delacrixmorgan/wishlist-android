package com.delacrixmorgan.wishlist.android.ui.overview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.common.bottomsheet.ActionBottomSheetFragment
import com.delacrixmorgan.wishlist.android.data.model.Budget
import com.delacrixmorgan.wishlist.android.data.model.BudgetRefreshEvent
import com.delacrixmorgan.wishlist.android.ui.BudgetNavigationFragment
import com.delacrixmorgan.wishlist.android.ui.create.CreateActivity
import kotlinx.android.synthetic.main.fragment_budget_list.*
import org.greenrobot.eventbus.EventBus

class BudgetListFragment : Fragment(),
    BudgetOverviewDetailFragment.Listener,
    ActionBottomSheetFragment.Listener {

    companion object {
        fun create() = BudgetListFragment()
    }

    enum class OverviewType {
        List,
        Spent
    }

    private lateinit var viewModel: BudgetListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_budget_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)
            .get(BudgetListViewModel::class.java)

        val fragments = listOf(
            BudgetOverviewDetailFragment.create(OverviewType.List, this),
            BudgetOverviewDetailFragment.create(OverviewType.Spent, this)
        )

        backButton.setOnClickListener {
            activity?.finish()
        }

        addViewGroup.setOnClickListener {
            val intent = CreateActivity.create(it.context)
            startActivity(intent)
        }

        radioGroup.setOnCheckedChangeListener { _, radioButtonId ->
            val fragment = when (radioButtonId) {
                R.id.leftRadioButton -> fragments[0]
                R.id.spentRadioButton -> fragments[1]
                else -> throw Exception()
            }

            updateContainerView(fragment)
        }
        radioGroup.check(R.id.leftRadioButton)
    }

    private fun updateContainerView(fragment: Fragment) {
        activity?.supportFragmentManager?.commit {
            replace(containerLayout.id, fragment, fragment::class.simpleName)
        }
    }

    override fun onBudgetSelected(budget: Budget) {
        viewModel.budget = budget
        val actionBottomSheetFragment = ActionBottomSheetFragment.create(
            ActionBottomSheetFragment.ActionType.Budget, this
        )
        actionBottomSheetFragment.show(
            childFragmentManager,
            ActionBottomSheetFragment::class.simpleName
        )
    }

    override fun onItemLaunched() {
        activity?.setResult(Activity.RESULT_OK, Intent().apply {
            putExtra(BudgetNavigationFragment.EXTRA_BUDGET_UUID, viewModel.budget?.uuid)
        })
        activity?.finish()
    }

    override fun onItemEdited() {
        //TODO Edit Budget
    }

    override fun onItemDeleted() {
        MaterialDialog(requireContext()).show {
            title(text = "Delete Budget")
            message(text = "Are you sure you want to delete?")
            positiveButton(text = "Delete") {
                viewModel.deleteBudget {
                    EventBus.getDefault().post(BudgetRefreshEvent(it))
                }
            }
            negativeButton(text = "Cancel")
        }
    }
}