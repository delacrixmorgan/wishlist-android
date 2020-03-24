package com.delacrixmorgan.wishlist.android.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.delacrixmorgan.wishlist.android.R
import kotlinx.android.synthetic.main.fragment_create_name.*

class CreateNameFragment : Fragment() {

    companion object {
        fun create(listener: CreateListeners) = CreateNameFragment().apply {
            this.listener = listener
        }
    }

    private lateinit var listener: CreateListeners

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameEditText.doAfterTextChanged {
            listener.onBudgetNameUpdate(it.toString())
        }
    }
}