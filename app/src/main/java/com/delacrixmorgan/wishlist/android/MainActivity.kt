package com.delacrixmorgan.wishlist.android

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.delacrixmorgan.wishlist.android.data.controller.BudgetDataController
import com.delacrixmorgan.wishlist.android.data.controller.TransactionDataController
import com.delacrixmorgan.wishlist.android.data.sync.SyncDataController
import com.delacrixmorgan.wishlist.android.ui.BudgetNavigationFragment
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        lifecycleScope.launch {
            BudgetDataController.loadBudget()
            TransactionDataController.loadTransaction()

            val fragment = BudgetNavigationFragment.create()
            supportFragmentManager.commit {
                replace(android.R.id.content, fragment, fragment.javaClass.simpleName)
            }
        }
    }
}