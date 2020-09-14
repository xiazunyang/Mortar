package com.numeorn.mortar

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import java.util.*

/**
 * 负责在Fragment的onViewCreated方法时，搜索对应的结果并传递给Fragment
 */
object MortarTransporter : FragmentManager.FragmentLifecycleCallbacks() {

    private val resultValues = LinkedList<TypedValue<*>>()

    private val resultCallbacks = LinkedList<TypedCallback<*>>()

    override fun onFragmentViewCreated(fm: FragmentManager, fragment: Fragment, view: View, savedInstanceState: Bundle?) {
        resultCallbacks
                //从回调缓存中找到所有从此界面中发起的回调
                .filter {
                    it.ownerHashCode == fragment.hashCode()
                }
                .forEach { typedCallback ->
                    //从结果缓存中找到所有类型一致的结果
                    val typedValue = resultValues.find {
                        it.type == typedCallback.type
                    }
                    if (typedValue != null) {
                        //如果有满足条件的结果，则执行回调并移除
                        @Suppress("UNCHECKED_CAST")
                        (typedCallback as TypedCallback<Any>).invoke(typedValue.result, typedValue.value)
                        resultValues.remove(typedValue)
                        resultCallbacks.remove(typedCallback)
                    } else {
                        // 如果没有，则移除掉其它未使用的回调，防止内存泄漏
                        resultCallbacks.remove(typedCallback)
                        //告知回调被移除，原因是未匹配到任何结果
                        typedCallback(RESULT_UNMATCHED, null)
                    }
                }
    }

    fun <T : Any> putCallback(callback: TypedCallback<T>) {
        Log.d("FragmentResult", "putCallback, type = [${callback.type}]")
        resultCallbacks.addFirst(callback)
    }

    fun <T : Any> putResult(value: TypedValue<T>) {
        if (resultCallbacks.any { it.type == value.type }) {
            Log.d("FragmentResult", "putResult, code = [${value.result}], type = [${value.type}]")
            resultValues.addFirst(value)
        }
    }

    internal fun clearResultValue() {
        resultValues.clear()
    }

    internal fun removeCallbacks(lifecycleOwner: LifecycleOwner) {
        resultCallbacks
                .filter {
                    it.ownerHashCode == lifecycleOwner.hashCode()
                }
                .map(resultCallbacks::remove)
    }

}