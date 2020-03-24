package com.delacrixmorgan.wishlist.android.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.delacrixmorgan.wishlist.android.R
import com.delacrixmorgan.wishlist.android.data.controller.BudgetDataController
import com.delacrixmorgan.wishlist.android.data.model.Budget
import com.delacrixmorgan.wishlist.android.data.model.BudgetRefreshEvent
import com.delacrixmorgan.wishlist.android.ui.create.CreateActivity
import com.delacrixmorgan.wishlist.android.ui.form.FormActivity
import com.delacrixmorgan.wishlist.android.ui.history.HistoryActivity
import com.delacrixmorgan.wishlist.android.ui.overview.BudgetOverviewActivity
import com.delacrixmorgan.wishlist.android.ui.setting.SettingActivity
import com.jem.liquidswipe.clippathprovider.LiquidSwipeClipPathProvider
import kotlinx.android.synthetic.main.fragment_budget_navigation.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class BudgetNavigationFragment : Fragment() {

    companion object {
        const val REQUEST_BUDGET_UUID = 1
        const val REQUEST_ONBOARDING = 2
        const val EXTRA_BUDGET_UUID = "Budget.uuid"

        fun create() = BudgetNavigationFragment()
    }

    private lateinit var budget: Budget

    private var currentPosition = 0
    private val budgetFragments = mutableListOf<BudgetPagerParams>()
    private val liquidSwipeClipPathProviders = mutableListOf<LiquidSwipeClipPathProvider>()

    private val adapter: BudgetPagerAdapter by lazy {
        BudgetPagerAdapter(requireActivity().supportFragmentManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_budget_navigation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)
        setupViews()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupViews() {
        budgetFragments.clear()

        BudgetDataController.budgets.forEach { budget ->
            val provider = LiquidSwipeClipPathProvider()
            liquidSwipeClipPathProviders.add(provider)
            budgetFragments.add(
                BudgetPagerParams(BudgetDetailFragment.create(budget, provider), budget)
            )
        }

        if (budgetFragments.isEmpty()) {
            val intent = CreateActivity.create(requireContext())
            startActivityForResult(intent, REQUEST_ONBOARDING)

            return
        }

        adapter.items = budgetFragments
        budget = BudgetDataController.budgets.first()

        nameTextView.text = budget.name
        budgetViewPager.adapter = adapter
        pageIndicator.setViewPager(budgetViewPager)

        budgetViewPager.setOnTouchListener { _, event ->
            val waveCenterY = event.y
            liquidSwipeClipPathProviders.map { it.waveCenterY = waveCenterY }
            false
        }
        budgetViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                currentPosition = position
                budget = BudgetDataController.budgets[currentPosition]
                nameTextView.text = budget.name
            }

            override fun onPageScrollStateChanged(state: Int) = Unit

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) = Unit
        })

        settingButton.setOnClickListener {
            val intent = SettingActivity.create(it.context)
            startActivity(intent)
        }

        budgetButton.setOnClickListener {
            val intent = BudgetOverviewActivity.create(it.context)
            startActivityForResult(intent, REQUEST_BUDGET_UUID)
        }

        spendingButton.setOnClickListener {
            val intent = FormActivity.create(it.context, budget)
            startActivity(intent)
        }

        historyButton.setOnClickListener {
            val intent = HistoryActivity.create(it.context, budget)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onBudget(budget: Budget) {
        val provider = LiquidSwipeClipPathProvider()
        liquidSwipeClipPathProviders.add(provider)
        budgetFragments.add(
            BudgetPagerParams(BudgetDetailFragment.create(budget, provider), budget)
        )

        adapter.items = budgetFragments
        adapter.notifyDataSetChanged()

        currentPosition = budgetFragments.size
        budgetViewPager.setCurrentItem(currentPosition, true)
    }

    @Subscribe
    fun onBudgetRefreshEvent(budgetRefreshEvent: BudgetRefreshEvent) {
        adapter.items.remove(budgetFragments.firstOrNull { it.budget == budgetRefreshEvent.budget })
        adapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_BUDGET_UUID -> {
                if (resultCode == Activity.RESULT_OK) {
                    val targetBudget = BudgetDataController.getBudgetById(
                        requireNotNull(data?.getStringExtra(EXTRA_BUDGET_UUID))
                    )
                    currentPosition = BudgetDataController.budgets.indexOf(targetBudget)
                    budgetViewPager.setCurrentItem(currentPosition, true)
                }
            }
            REQUEST_ONBOARDING -> {
                setupViews()
            }
        }
    }
}