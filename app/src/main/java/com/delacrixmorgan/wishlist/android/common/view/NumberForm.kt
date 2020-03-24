package com.delacrixmorgan.wishlist.android.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.common.*
import com.delacrixmorgan.wishlist.android.data.controller.CurrencyDataController
import com.delacrixmorgan.wishlist.android.data.model.Money
import kotlinx.android.synthetic.main.layout_number_form.view.*

class NumberForm(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs),
    FormProtocol<Money> {

    interface Listener : ListenerProtocol {
        fun onAmountUpdated(amountInCents: Long)
        fun onSubmitted(amountInCents: Long)
    }

    private var amountInCents = 0L
    private var listener: Listener? = null

    init {
        setup()
    }

    private fun setup() {
        inflate(context, R.layout.layout_number_form, this)

        oneTextView.setOnClickListener { addAmount("1") }
        twoTextView.setOnClickListener { addAmount("2") }
        threeTextView.setOnClickListener { addAmount("3") }
        fourTextView.setOnClickListener { addAmount("4") }
        fiveTextView.setOnClickListener { addAmount("5") }
        sixTextView.setOnClickListener { addAmount("6") }
        sevenTextView.setOnClickListener { addAmount("7") }
        eightTextView.setOnClickListener { addAmount("8") }
        nineTextView.setOnClickListener { addAmount("9") }
        zeroTextView.setOnClickListener { addAmount("0") }

        backspaceButton.setOnClickListener {
            if (amountInCents != 0L) it.performHapticContextClick()
            subtractAmount()
        }

        submitImageView.setOnClickListener {
            it.performHapticContextClick()
            if (isValid()) {
                listener?.onSubmitted(amountInCents)
            }
        }
    }
    
    override fun configure(model: Money?, listener: ListenerProtocol?) {
        if (model != null) {
            amountInCents = model.amount.dollarToCents()
            amountTextView.text = amountInCents.moneyFormat()
        }

        if (listener != null) {
            this.listener = listener as Listener
        }
    }

    override fun model(): Money {
        return Money(
            currency = CurrencyDataController.selectedCurrency,
            amount = "${amountTextView.text}".toDouble()
        )
    }

    private fun addAmount(newString: String) {
        if (amountInCents.length() == 13) return
        amountInCents = "${amountInCents}$newString".toLong()
        amountTextView.text = amountInCents.centsToDollar().moneyFormat()

        listener?.onAmountUpdated(amountInCents)
    }

    private fun subtractAmount() {
        val newAmount = amountInCents.toString().dropLast(1)
        amountInCents = if (newAmount.isNotBlank()) newAmount.toLong() else 0
        amountTextView.text = amountInCents.centsToDollar().moneyFormat()

        listener?.onAmountUpdated(amountInCents)
    }

    fun configureAndUpdateSummary(model: Money?) {
        if (model != null) {
            amountInCents = model.amount.dollarToCents()
            amountTextView.text = amountInCents.centsToDollar().moneyFormat()
            listener?.onAmountUpdated(amountInCents)
        }
    }
}