package com.numeorn.mortar

import java.lang.reflect.*

abstract class TypedToken<T : Any> {

    val type: Type

    init {
        //获取详细的类型
        val parameterizedType = javaClass.genericSuperclass as ParameterizedType
        type = parameterizedType.actualTypeArguments.first()
    }

    override fun toString(): String {
        return type.toString()
    }

}