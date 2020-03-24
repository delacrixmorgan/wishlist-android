package com.delacrixmorgan.wishlist.android.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.data.controller.BudgetDataController
import com.delacrixmorgan.wishlist.android.data.model.Budget
import com.delacrixmorgan.wishlist.android.data.model.Keys
import kotlinx.android.synthetic.main.fragment_create_completion.*

class CreateCompletionFragment : Fragment() {

    companion object {
        fun create(budget: Budget) = CreateCompletionFragment().apply {
            arguments = bundleOf(Keys.CreateCompletion.BudgetId.name to budget.uuid)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_completion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val budget = BudgetDataController.getBudgetById(
            requireNotNull(arguments?.getString(Keys.CreateCompletion.BudgetId.name))
        )

        titleTextView.text = "${budget?.name} Created"

        createButton.setOnClickListener {
            val fragment = CreateListFragment.create()
            requireActivity().supportFragmentManager.commit {
                replace(android.R.id.content, fragment, fragment::class.simpleName)
            }
        }

        doneButton.setOnClickListener {
            activity?.finish()
        }
    }
}