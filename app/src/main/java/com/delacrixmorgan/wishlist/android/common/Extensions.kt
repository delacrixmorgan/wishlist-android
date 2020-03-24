package com.delacrixmorgan.wishlist.android.common

import android.content.Context
import android.content.Intent
import android.graphics.ColorFilter
import android.net.Uri
import android.os.Build
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.fragment.app.Fragment
import com.delacrixmorgan.wishlist.android.data.controller.CurrencyDataController
import com.delacrixmorgan.wishlist.android.data.model.Money
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.log10

fun Int.getBlendModeFilter(blendMode: BlendModeCompat = BlendModeCompat.SRC): ColorFilter? {
    return BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
        this,
        blendMode
    )
}

fun Long.length() = when (this) {
    0L -> 1
    else -> log10(abs(toDouble())).toInt() + 1
}

fun Long.centsToDollar(): Double {
    return this / 100.0
}

fun Double.dollarToCents(): Long {
    return this.toLong() * 100L
}

fun Money.moneyFormat(): String {
    return this.amount.moneyFormat()
}

fun Long.moneyFormat(): String {
    return this.toDouble().moneyFormat()
}

fun Double.moneyFormat(): String {
    val currency = CurrencyDataController.selectedCurrency
    var targetDouble = this
    val formatString = if (targetDouble >= 0) {
        ""
    } else {
        targetDouble *= -1
        "-"
    }

    val bigDecimal = BigDecimal(targetDouble)
        .setScale(2, BigDecimal.ROUND_HALF_UP)
    val formattedAmount = DecimalFormat("###,##0.00").format(bigDecimal)


    return formatString + currency.symbol + " " + formattedAmount
}

fun Fragment.launchWebsite(url: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }
    startActivity(intent)
}

/**
 * Date
 */

fun Date.getCalendarField(fieldId: Int): Int {
    return Calendar.getInstance().apply { time = this@getCalendarField }.get(fieldId)
}

fun Date.formatDate(format: String = "EEE, dd MMMM"): String {
    return SimpleDateFormat(format, Locale.US).format(this)
}

/**
 * View
 */

fun View.performHapticContextClick() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
    } else {
        performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
    }
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showToast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}