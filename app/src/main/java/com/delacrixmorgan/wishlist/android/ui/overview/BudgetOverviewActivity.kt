package com.delacrixmorgan.wishlist.android.ui.overview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

class BudgetOverviewActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent {
            return Intent(context, BudgetOverviewActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragment = BudgetListFragment.create()
        supportFragmentManager.commit {
            replace(android.R.id.content, fragment, fragment::class.simpleName)
        }
    }
}