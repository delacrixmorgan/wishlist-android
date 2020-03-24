package com.delacrixmorgan.wishlist.android.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.data.category.BudgetType
import com.delacrixmorgan.wishlist.android.data.model.Budget
import com.delacrixmorgan.wishlist.android.data.model.Money
import kotlinx.android.synthetic.main.fragment_create_list.*
import kotlinx.coroutines.launch

class CreateListFragment : Fragment(), CreateListeners {

    companion object {
        fun create() = CreateListFragment()
    }

    inner class PagerAdapter : FragmentStateAdapter(requireActivity()) {
        override fun getItemCount() = fragments.size
        override fun createFragment(position: Int) = fragments[position]
    }

    private var currentPosition = 0
    private var fragments = mutableListOf<Fragment>()

    private lateinit var adapter: PagerAdapter
    private lateinit var viewModel: CreateListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)
            .get(CreateListViewModel::class.java)

        fragments = mutableListOf(
            CreateNameFragment.create(this),
            CreateTypeFragment.create(this),
            CreateAmountFragment.create(this)
        )

        adapter = PagerAdapter()

        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false

        closeButton.setOnClickListener {
            activity?.finish()
        }

        backButton.setOnClickListener {
            (--currentPosition).coerceAtLeast(0)
            viewPager.currentItem = currentPosition
            updateViews()
        }

        actionButton.setOnClickListener {
            if (currentPosition == fragments.size - 1 && viewModel.isValidProceeding()) {
                createBudget()
                return@setOnClickListener
            }

            currentPosition = (++currentPosition).coerceAtMost(fragments.size - 1)
            viewPager.currentItem = currentPosition

            updateViews()
        }

        updateViews()
    }

    private fun createBudget() {
        lifecycleScope.launch {
            viewModel.createBudget {
                launchCompletionFragment(it)
            }
        }
    }

    private fun launchCompletionFragment(budget: Budget) {
        val fragment = CreateCompletionFragment.create(budget)
        requireActivity().supportFragmentManager.commit {
            replace(android.R.id.content, fragment, fragment::class.simpleName)
        }
    }

    private fun updateViews() {
        actionButton.isEnabled = viewModel.isActionButtonEnabled(currentPosition)

        if (currentPosition == 0) {
            closeButton.isVisible = true
            backButton.isVisible = false
        } else {
            closeButton.isVisible = true
            backButton.isVisible = true
        }

        if (currentPosition == fragments.size - 1) {
            actionButton.setImageResource(R.drawable.ic_done)
        } else {
            actionButton.setImageResource(R.drawable.ic_arrow_forward)
        }
    }

    override fun onBudgetNameUpdate(name: String) {
        viewModel.update(name = name)
        updateViews()
    }

    override fun onBudgetAmountSelected(amount: Money) {
        viewModel.update(amount = amount)
        updateViews()
    }

    override fun onBudgetTypeSelected(budgetType: BudgetType) {
        viewModel.update(type = budgetType)
        updateViews()
    }

    override fun onBudgetRolloverToggled(isEnabled: Boolean) {
        viewModel.update(isRolloverEnabled = isEnabled)
        updateViews()
    }
}