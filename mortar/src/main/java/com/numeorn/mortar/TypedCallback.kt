package com.numeorn.mortar

import androidx.lifecycle.LifecycleOwner

abstract class TypedCallback<T : Any>(owner: LifecycleOwner, private val callback: (Int, T?) -> Unit) : TypedToken<T>() {

    val ownerHashCode: Int = owner.hashCode()

    init {
        owner.lifecycle.addObserver(FragmentResultCleaner())
    }

    operator fun invoke(result: Int, value: T?) {
        callback(result, value)
    }

    override fun toString(): String {
        return "{ownerHashCode=$ownerHashCode, type=$type}"
    }

}