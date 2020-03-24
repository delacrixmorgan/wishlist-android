package com.delacrixmorgan.wishlist.android.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.widget.doAfterTextChanged
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.common.FormProtocol
import com.delacrixmorgan.wishlist.android.common.ListenerProtocol
import kotlinx.android.synthetic.main.layout_general_form.view.*

class GeneralForm(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs),
    FormProtocol<String> {

    init {
        inflate(context, R.layout.layout_general_form, this)
        setupViewGroups()
    }

    private var description: String = ""

    override fun configure(model: String?, listener: ListenerProtocol?) {
        model?.let {
            description = it
            descriptionTextView.setText(it)
        }
    }

    override fun model(): String? {
        return description
    }

    private fun setupViewGroups() {
        descriptionTextView.doAfterTextChanged {
            description = it.toString()
        }
    }
}