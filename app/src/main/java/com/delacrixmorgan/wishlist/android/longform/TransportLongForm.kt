package com.delacrixmorgan.wishlist.android.longform

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.common.FormProtocol
import com.delacrixmorgan.wishlist.android.common.ListenerProtocol
import com.delacrixmorgan.wishlist.android.common.centsToDollar
import com.delacrixmorgan.wishlist.android.common.view.NumberForm
import com.delacrixmorgan.wishlist.android.data.category.TransportType
import com.delacrixmorgan.wishlist.android.data.model.*
import kotlinx.android.synthetic.main.layout_transport_long_form.view.*
import java.util.*

class TransportLongForm(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs),
    FormProtocol<Budget>, NumberForm.Listener {

    init {
        inflate(context, R.layout.layout_transport_long_form, this)
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

        val transportModel = TransportModel(
            budget,
            TransportType.Petrol,
            ""
        )

        val numberFormMoney = Money(0.0)

        summaryForm.configure(summaryModel)
        transportForm.configure(transportModel)
        numberForm.configure(numberFormMoney, this)

//        transportForm.setOnCheckedChanged { position ->
//            val money = foodModel.moneyOptions[position]
//            numberForm.configureAndUpdateSummary(money)
//        }
    }

    /**
     *  NumberForm.Listener
     */

    override fun onAmountUpdated(amountInCents: Long) {
        transaction?.amountTransaction?.amount = amountInCents.centsToDollar()
        summaryForm.updateAmount(budget, transaction)

        // TODO TransportForm Update ProgressBar
//        foodForm.resetViewGroupState()
    }

    override fun onSubmitted(amountInCents: Long) {
        val name =
            if (transportForm?.model()?.remark == null || transportForm?.model()?.remark?.isBlank() == true) {
                "Transport"
            } else {
                transportForm?.model()?.remark
            }
        transaction?.name = name.toString()
        transaction?.createdAt = Date()
        transaction?.amountTransaction?.amount = amountInCents.centsToDollar()
        listener?.onLongFormSubmitted(requireNotNull(transaction))
    }
}