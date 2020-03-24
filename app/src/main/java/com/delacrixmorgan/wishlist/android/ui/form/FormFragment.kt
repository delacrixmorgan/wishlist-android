package com.delacrixmorgan.wishlist.android.ui.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.data.category.BudgetType
import com.delacrixmorgan.wishlist.android.data.controller.BudgetDataController
import com.delacrixmorgan.wishlist.android.data.controller.TransactionDataController
import com.delacrixmorgan.wishlist.android.data.model.Keys
import com.delacrixmorgan.wishlist.android.data.model.Transaction
import com.delacrixmorgan.wishlist.android.longform.LongFormListener
import kotlinx.android.synthetic.main.fragment_form.*
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class FormFragment : Fragment(), LongFormListener {
    companion object {
        fun create(budgetId: String) = FormFragment().apply {
            arguments = bundleOf(
                Keys.Form.BudgetId.name to budgetId
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val budget = BudgetDataController.getBudgetById(
            requireNotNull(arguments?.getString(Keys.Form.BudgetId.name))
        )

        when (budget?.type) {
            BudgetType.General -> {
                generalLongForm.isVisible = true
                generalLongForm.configure(budget, this)
            }
            BudgetType.Food -> {
                foodLongForm.isVisible = true
                foodLongForm.configure(budget, this)
            }
            BudgetType.Transport -> {
                transportLongForm.isVisible = true
                transportLongForm.configure(budget, this)
            }
        }
    }

    override fun onLongFormSubmitted(transaction: Transaction) {
        lifecycleScope.launch {
            TransactionDataController.insertTransaction(transaction) {
                EventBus.getDefault().post(transaction)
                activity?.finish()
            }
        }
    }
}