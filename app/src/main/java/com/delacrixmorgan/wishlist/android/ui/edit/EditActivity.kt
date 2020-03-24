package com.delacrixmorgan.wishlist.android.ui.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

class EditActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent {
            return Intent(context, EditActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragment = EditFormFragment.create()

        supportFragmentManager.commit {
            replace(android.R.id.content, fragment, fragment::class.simpleName)
        }
    }
}