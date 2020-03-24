package com.delacrixmorgan.wishlist.android.longform

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.common.FormProtocol
import com.delacrixmorgan.wishlist.android.common.ListenerProtocol
import com.delacrixmorgan.wishlist.android.common.centsToDollar
import com.delacrixmorgan.wishlist.android.common.view.NumberForm
import com.delacrixmorgan.wishlist.android.data.model.*
import kotlinx.android.synthetic.main.layout_food_long_form.view.*
import java.util.*

class FoodLongForm(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs),
    FormProtocol<Budget>, NumberForm.Listener {

    private lateinit var budget: Budget

    private var transaction: Transaction? = null
    private var listener: LongFormListener? = null

    init {
        inflate(context, R.layout.layout_food_long_form, this)
    }

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

        val foodModel = FoodModel(
            listOf(
                Money(6.0),
                Money(8.0),
                Money(15.0)
            )
        )

        val numberFormMoney = Money(0.0)

        summaryForm.configure(summaryModel)
        foodForm.configure(foodModel)
        numberForm.configure(numberFormMoney, this)

        foodForm.setOnCheckedChanged { position ->
            val money = foodModel.moneyOptions[position]
            numberForm.configureAndUpdateSummary(money)
        }
    }

    /**
     *  NumberForm.Listener
     */

    override fun onAmountUpdated(amountInCents: Long) {
        transaction?.amountTransaction?.amount = amountInCents.centsToDollar()
        summaryForm.updateAmount(budget, transaction)
        foodForm.resetViewGroupState()
    }

    override fun onSubmitted(amountInCents: Long) {
        val name =
            if (foodForm?.model()?.location == null || foodForm?.model()?.location?.isBlank() == true) {
                "Food"
            } else {
                foodForm?.model()?.location
            }
        transaction?.name = name.toString()
        transaction?.createdAt = Date()
        transaction?.amountTransaction?.amount = amountInCents.centsToDollar()
        listener?.onLongFormSubmitted(requireNotNull(transaction))
    }
}