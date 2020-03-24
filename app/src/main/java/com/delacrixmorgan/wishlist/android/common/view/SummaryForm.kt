package com.delacrixmorgan.wishlist.android.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.common.FormProtocol
import com.delacrixmorgan.wishlist.android.common.ListenerProtocol
import com.delacrixmorgan.wishlist.android.common.moneyFormat
import com.delacrixmorgan.wishlist.android.data.model.Budget
import com.delacrixmorgan.wishlist.android.data.model.SummaryModel
import com.delacrixmorgan.wishlist.android.data.model.Transaction
import kotlinx.android.synthetic.main.layout_summary_form.view.*

class SummaryForm(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs),
    FormProtocol<SummaryModel> {

    init {
        setup()
    }

    private fun setup() {
        inflate(context, R.layout.layout_summary_form, this)
    }

    override fun configure(model: SummaryModel?, listener: ListenerProtocol?) {
        if (model != null) {
            descriptionTextView.text = model.description
            updateAmount(model.budget)
        }
    }

    override fun model(): SummaryModel? {
        return null
    }

    fun updateAmount(budget: Budget, transaction: Transaction? = null) {
        val amountLeft = budget.amountLeft.amount - (transaction?.amountTransaction?.amount ?: 0.0)
        moneyTextView.text = amountLeft.moneyFormat()
    }
}