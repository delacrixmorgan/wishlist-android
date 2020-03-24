package com.delacrixmorgan.wishlist.android.common.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.common.FormProtocol
import com.delacrixmorgan.wishlist.android.common.ListenerProtocol
import com.delacrixmorgan.wishlist.android.common.getBlendModeFilter
import com.delacrixmorgan.wishlist.android.common.performHapticContextClick
import com.delacrixmorgan.wishlist.android.data.model.TransportModel
import com.delacrixmorgan.wishlist.android.data.category.TransportType
import kotlinx.android.synthetic.main.layout_transport_form.view.*

class TransportForm(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs),
    FormProtocol<TransportModel> {

    init {
        inflate(context, R.layout.layout_transport_form, this)
        setupViewGroups()
    }

    private var transportType: TransportType = TransportType.Petrol
        set(value) {
            field = value
            transportModel?.type = value
        }
    private var transportModel: TransportModel? = null
    private lateinit var optionViewGroups: List<FrameLayout>

    override fun configure(model: TransportModel?, listener: ListenerProtocol?) {
        model?.let {
            transportModel = it
            updateDescriptionTextView(transportType.ordinal)
        }
    }

    override fun model(): TransportModel? {
        return transportModel
    }

    private fun setupViewGroups() {
        optionViewGroups = listOf<FrameLayout>(petrolViewGroup, carViewGroup, subwayViewGroup)
        optionViewGroups.forEachIndexed { index, frameLayout ->
            frameLayout.setOnClickListener {
                it.performHapticContextClick()
                descriptionViewGroup.isVisible = true

                updateViewGroupState(index)
                updateDescriptionTextView(index)
            }
        }

        descriptionTextView.doAfterTextChanged {
            transportModel?.remark = it.toString()
        }
    }

    private fun updateViewGroupState(position: Int?) {
        val colorAccent = ContextCompat.getColor(context, R.color.colorAccent)
        val colorInactive = ContextCompat.getColor(context, R.color.colorInactive)
        val colorGrey = ContextCompat.getColor(context, R.color.colorGrey)

        optionViewGroups.forEachIndexed { index, frameLayout ->
            if (index == position) {
                frameLayout.background.colorFilter = colorAccent.getBlendModeFilter()
                (frameLayout.getChildAt(0) as ImageView).apply {
                    clearColorFilter()
                    setColorFilter(Color.WHITE)
                }
            } else {
                frameLayout.background.colorFilter = colorInactive.getBlendModeFilter()
                (frameLayout.getChildAt(0) as ImageView).apply {
                    clearColorFilter()
                    setColorFilter(colorGrey)
                }
            }
        }
    }

    private fun updateDescriptionTextView(position: Int?) {
        val description = when (position) {
            0 -> "Petrol"
            1 -> "Toll"
            2 -> "Train"
            else -> ""
        }
        descriptionTextView.setText(description)
    }
}