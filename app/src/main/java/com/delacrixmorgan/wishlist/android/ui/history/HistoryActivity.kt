package com.delacrixmorgan.wishlist.android.ui.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.delacrixmorgan.wishlist.android.data.model.Budget
import com.delacrixmorgan.wishlist.android.data.model.Keys

class HistoryActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context, budget: Budget): Intent {
            return Intent(context, HistoryActivity::class.java).apply {
                putExtra(Keys.History.BudgetId.name, budget.uuid)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val budgetId = requireNotNull(intent.getStringExtra(Keys.History.BudgetId.name))
        val fragment = HistoryListFragment.create(budgetId)
        
        supportFragmentManager.commit {
            replace(android.R.id.content, fragment, fragment::class.simpleName)
        }
    }
}