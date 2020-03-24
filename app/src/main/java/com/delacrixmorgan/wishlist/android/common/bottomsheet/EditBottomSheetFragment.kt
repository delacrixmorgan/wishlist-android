package com.delacrixmorgan.wishlist.android.common.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.data.model.Keys
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditBottomSheetFragment : BottomSheetDialogFragment() {
    companion object {
        fun create(listener: Listener, title: String? = null) = EditBottomSheetFragment().apply {
            arguments = bundleOf(Keys.EditBottomSheet.Title.name to title)
            this.listener = listener
        }
    }

    interface Listener {
        fun onEditSubmit(title: String)
    }

    private lateinit var listener: Listener

    private val title: String?
        get() = arguments?.getString(Keys.EditBottomSheet.Title.name)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_edit_bottom_sheet,
            container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}