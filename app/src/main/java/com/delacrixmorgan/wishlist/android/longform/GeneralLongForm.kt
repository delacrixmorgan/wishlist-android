package com.delacrixmorgan.wishlist.android.longform

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.common.FormProtocol
import com.delacrixmorgan.wishlist.android.common.ListenerProtocol
import com.delacrixmorgan.wishlist.android.common.centsToDollar
import com.delacrixmorgan.wishlist.android.common.view.NumberForm
import com.delacrixmorgan.wishlist.android.data.model.Budget
import com.delacrixmorgan.wishlist.android.data.model.Money
import com.delacrixmorgan.wishlist.android.data.model.SummaryModel
import com.delacrixmorgan.wishlist.android.data.model.Transaction
import kotlinx.android.synthetic.main.layout_general_long_form.view.*
import java.util.*

class GeneralLongForm(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs),
    FormProtocol<Budget>, NumberForm.Listener {

    init {
        inflate(context, R.layout.layout_general_long_form, this)
    }

    private lateinit var budget: Budget

    private var transaction: Transaction? = null
    private var listener: LongFormListener? = null

    override fun configure(model: Budget?, listener: ListenerProtocol?) {
        model?.let {
            budget = it
            setup()
        }
        this.listener = listener as LongFormListener
    }

    override fun model(): Budget? {
        return budget
    }

    private fun setup() {
        transaction = Transaction(
            amountTransaction = Money(0.0),
            name = "Food",
            budgetUuid = budget.uuid,
            budgetType = budget.type
        )

        val summaryModel = SummaryModel(
            budget,
            "Left this month"
        )

        val numberFormMoney = Money(0.0)

        summaryForm.configure(summaryModel)
        generalForm.configure("")
        numberForm.configure(numberFormMoney, this)
    }

    /**
     *  NumberForm.Listener
     */

    override fun onAmountUpdated(amountInCents: Long) {
        transaction?.amountTransaction?.amount = amountInCents.centsToDollar()
        summaryForm.updateAmount(budget, transaction)
    }

    override fun onSubmitted(amountInCents: Long) {
        val name =
            if (generalForm?.model() == null || generalForm?.model()?.isBlank() == true) {
                "Notes"
            } else {
                generalForm?.model()
            }
        transaction?.name = name.toString()
        transaction?.createdAt = Date()
        transaction?.amountTransaction?.amount = amountInCents.centsToDollar()
        listener?.onLongFormSubmitted(requireNotNull(transaction))
    }
}