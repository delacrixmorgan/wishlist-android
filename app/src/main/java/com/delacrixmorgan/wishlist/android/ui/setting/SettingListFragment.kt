package com.delacrixmorgan.wishlist.android.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.delacrixmorgan.wishlist.android.BuildConfig
import com.delacrixmorgan.wishlist.android.databinding.FragmentSettingListBinding
import kotlinx.android.synthetic.main.fragment_setting_list.*

class SettingListFragment : Fragment() {
    companion object {
        fun create() = SettingListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentSettingListBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buildNumberTextView.text = "v${BuildConfig.VERSION_NAME} #${BuildConfig.VERSION_CODE}"

        backButton.setOnClickListener {
            activity?.finish()
        }

        supportViewGroup.setOnClickListener {
            val supportDialogFragment = SettingSupportDialogFragment.create()
            supportDialogFragment.show(
                requireActivity().supportFragmentManager,
                supportDialogFragment.javaClass.simpleName
            )
        }
    }
}