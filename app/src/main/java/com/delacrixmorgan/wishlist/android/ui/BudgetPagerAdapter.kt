package com.delacrixmorgan.wishlist.android.ui

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.delacrixmorgan.wishlist.android.data.model.Budget

class BudgetPagerAdapter(supportFragmentManager: FragmentManager) :
    FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var items = mutableListOf<BudgetPagerParams>()

    override fun getCount() = items.size
    override fun getItem(position: Int) = items[position].fragment
}

data class BudgetPagerParams(
    val fragment: BudgetDetailFragment,
    val budget: Budget
)