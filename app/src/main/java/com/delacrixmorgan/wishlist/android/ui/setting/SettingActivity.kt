package com.delacrixmorgan.wishlist.android.ui.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

class SettingActivity : AppCompatActivity() {
    companion object {
        fun create(context: Context): Intent {
            return Intent(context, SettingActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragment = SettingListFragment.create()

        supportFragmentManager.commit {
            replace(android.R.id.content, fragment, fragment::class.simpleName)
        }
    }
}