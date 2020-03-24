package com.delacrixmorgan.wishlist.android.common.view

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.common.*
import com.delacrixmorgan.wishlist.android.data.model.FoodModel
import com.delacrixmorgan.wishlist.android.data.model.Money
import kotlinx.android.synthetic.main.layout_food_form.view.*

class FoodForm(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs),
    FormProtocol<FoodModel> {

    private var foodModel: FoodModel? = null
    private var listener: ((position: Int) -> Unit)? = null
    private lateinit var optionViewGroups: List<FrameLayout>

    init {
        inflate(context, R.layout.layout_food_form, this)
    }

    override fun configure(model: FoodModel?, listener: ListenerProtocol?) {
        foodModel = model
        setupViewGroups(model?.moneyOptions)
        locationEditText.setText(model?.location)
    }

    override fun model(): FoodModel? {
        return foodModel
    }

    private fun setupViewGroups(moneyOptions: List<Money>?) {
        moneyOptions ?: return

        optionViewGroups = listOf<FrameLayout>(smallViewGroup, mediumViewGroup, largeViewGroup)
        optionViewGroups.forEachIndexed { index, frameLayout ->
            (frameLayout.getChildAt(0) as TextView).apply {
                text = moneyOptions[index].amount.moneyFormat()
                frameLayout.setOnClickListener {
                    it.performHapticContextClick()
                    listener?.invoke(index)
                    updateViewGroupState(index)
                }
            }
        }

        locationEditText.doAfterTextChanged {
            foodModel?.location = it.toString()
        }
    }

    private fun updateViewGroupState(position: Int?) {
        val colorAccent = ContextCompat.getColor(context, R.color.colorAccent)
        val colorInactive = ContextCompat.getColor(context, R.color.colorInactive)
        val colorInactiveHint = ContextCompat.getColor(context, R.color.colorInactiveHint)

        optionViewGroups.forEachIndexed { index, frameLayout ->
            if (index == position) {
                frameLayout.background.colorFilter = colorAccent.getBlendModeFilter()
                (frameLayout.getChildAt(0) as TextView).apply {
                    setTextColor(Color.WHITE)
                    setTypeface(null, Typeface.BOLD)
                }
            } else {
                frameLayout.background.colorFilter = colorInactive.getBlendModeFilter()
                (frameLayout.getChildAt(0) as TextView).apply {
                    setTextColor(colorInactiveHint)
                    setTypeface(null, Typeface.NORMAL)
                }
            }
        }
    }

    fun setOnCheckedChanged(listener: (position: Int) -> Unit) {
        this.listener = listener
    }

    fun resetViewGroupState() {
        updateViewGroupState(null)
    }
}