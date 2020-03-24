package com.delacrixmorgan.wishlist.android.common.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.data.model.Keys
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_action_bottom_sheet.*

class ActionBottomSheetFragment : BottomSheetDialogFragment() {
    companion object {
        fun create(
            actionType: ActionType, listener: Listener
        ) = ActionBottomSheetFragment().apply {
            arguments = bundleOf(Keys.ActionBottomSheet.ActionType.name to actionType)
            this.listener = listener
        }
    }

    enum class ActionType {
        Budget,
        Transaction
    }

    interface Listener {
        fun onItemLaunched() {}
        fun onItemEdited()
        fun onItemDeleted()
    }

    private lateinit var listener: Listener

    private val actionType: ActionType
        get() = arguments?.getSerializable(Keys.ActionBottomSheet.ActionType.name) as ActionType

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_action_bottom_sheet,
            container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (actionType) {
            ActionType.Budget -> Unit
            ActionType.Transaction -> launchViewGroup.isVisible = false
        }

        launchViewGroup.setOnClickListener {
            listener.onItemLaunched()
            dismiss()
        }

        editViewGroup.setOnClickListener {
            listener.onItemEdited()
            dismiss()
        }

        deleteViewGroup.setOnClickListener {
            listener.onItemDeleted()
            dismiss()
        }
    }
}