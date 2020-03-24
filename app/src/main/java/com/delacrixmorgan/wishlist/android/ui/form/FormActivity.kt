package com.delacrixmorgan.wishlist.android.ui.form

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.delacrixmorgan.wishlist.android.data.model.Budget
import com.delacrixmorgan.wishlist.android.data.model.Keys

class FormActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context, budget: Budget): Intent {
            val intent = Intent(context, FormActivity::class.java)
            intent.putExtra(Keys.Form.BudgetId.name, budget.uuid)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val budgetId = requireNotNull(intent.getStringExtra(Keys.Form.BudgetId.name))
        val fragment = FormFragment.create(budgetId)
        
        supportFragmentManager.commit {
            replace(android.R.id.content, fragment, fragment::class.simpleName)
        }
    }
}