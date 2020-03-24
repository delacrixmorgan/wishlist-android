package com.delacrixmorgan.wishlist.android.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.common.launchWebsite
import kotlinx.android.synthetic.main.fragment_setting_support_dialog.*

class SettingSupportDialogFragment : DialogFragment() {
    companion object {
        fun create() = SettingSupportDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting_support_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        materialDialogButton.setOnClickListener {
            launchWebsite("https://github.com/afollestad/material-dialogs")
        }

        threeTenABPButton.setOnClickListener {
            launchWebsite("https://github.com/JakeWharton/ThreeTenABP")
        }

        timberButton.setOnClickListener {
            launchWebsite("https://github.com/JakeWharton/timber")
        }

        pageInkButton.setOnClickListener {
            launchWebsite("https://github.com/DavidPacioianu/InkPageIndicator")
        }

        liquidSwipeButton.setOnClickListener {
            launchWebsite("https://github.com/Chrisvin/LiquidSwipe")
        }

        eventBusButton.setOnClickListener {
            launchWebsite("https://github.com/greenrobot/EventBus")
        }

        doneButton.setOnClickListener {
            dismiss()
        }
    }
}