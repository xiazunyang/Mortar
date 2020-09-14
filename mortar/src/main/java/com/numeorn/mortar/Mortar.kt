@file:JvmName("Mortar")

package com.numeorn.mortar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

const val RESULT_OK = 1

const val RESULT_CANCELED = 0

const val RESULT_UNMATCHED = -1

inline fun <reified A : FragmentActivity> A.mortarEnabled() {
    supportFragmentManager.registerFragmentLifecycleCallbacks(MortarTransporter, true)
}

fun clearResultValue() {
    MortarTransporter.clearResultValue()
}

inline fun <reified T : Any> Fragment.setResult(result: Int, value: T? = null) {
    val typedValue = object : TypedValue<T>(result, value) {}
    MortarTransporter.putResult(typedValue)
}

inline fun <reified T : Any> Fragment.onResult(noinline callback: (resultCode: Int, result: T?) -> Unit) {
    val typedCallback = object : TypedCallback<T>(this@onResult, callback) {}
    MortarTransporter.putCallback(typedCallback)
}
