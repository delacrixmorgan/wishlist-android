package com.delacrixmorgan.wishlist.android.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.data.category.DateType
import com.delacrixmorgan.wishlist.android.data.model.Keys
import kotlinx.android.synthetic.main.fragment_history_list.*

class HistoryListFragment : Fragment() {

    companion object {
        fun create(budgetId: String) = HistoryListFragment().apply {
            arguments = bundleOf(Keys.History.BudgetId.name to budgetId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val budgetId = requireNotNull(
            arguments?.getString(Keys.History.BudgetId.name)
        )

        val fragments = listOf(
            HistoryDetailFragment.create(budgetId, DateType.Day),
            HistoryDetailFragment.create(budgetId, DateType.Month),
            HistoryDetailFragment.create(budgetId, DateType.Year)
        )

        backButton.setOnClickListener {
            activity?.finish()
        }

        radioGroup.setOnCheckedChangeListener { _, radioButtonId ->
            val fragment = when (radioButtonId) {
                R.id.dayRadioButton -> fragments[0]
                R.id.monthRadioButton -> fragments[1]
                R.id.yearRadioButton -> fragments[2]
                else -> throw Exception()
            }
            updateContainerView(fragment)
        }

        radioGroup.check(R.id.dayRadioButton)
    }

    private fun updateContainerView(fragment: Fragment) {
        activity?.supportFragmentManager?.commit {
            replace(containerLayout.id, fragment, fragment::class.simpleName)
        }
    }
}