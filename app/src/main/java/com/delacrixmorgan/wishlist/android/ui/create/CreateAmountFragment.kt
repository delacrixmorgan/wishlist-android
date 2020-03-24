package com.delacrixmorgan.wishlist.android.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.data.model.Money
import kotlinx.android.synthetic.main.fragment_create_amount.*

class CreateAmountFragment : Fragment() {

    companion object {
        fun create(listener: CreateListeners) = CreateAmountFragment().apply {
            this.listener = listener
        }
    }

    private lateinit var listener: CreateListeners

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_amount, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        amountEditText.doAfterTextChanged {
            val money = Money(it.toString().toDouble())
            listener.onBudgetAmountSelected(money)
        }

        rolloverSwitch.setOnCheckedChangeListener { _, isChecked ->
            listener.onBudgetRolloverToggled(isChecked)
        }
    }
}