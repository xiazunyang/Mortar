package com.numeorn.mortar

abstract class TypedValue<T : Any>(val result: Int, val value: T?) : TypedToken<T>() {

    override fun hashCode(): Int {
        return type.hashCode() * 3 + value.hashCode() * 3
    }

    override fun equals(other: Any?): Boolean {
        return other is TypedValue<*> && other.type == type && other.value == value
    }

    override fun toString(): String {
        return "{type=$type, value=$value}"
    }

}