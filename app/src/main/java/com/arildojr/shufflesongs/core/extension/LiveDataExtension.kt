package com.arildojr.shufflesongs.core.extension

import androidx.lifecycle.MutableLiveData

infix fun <T> MutableLiveData<List<T>>.add(data: List<T>?) {
    val newValue = mutableListOf<T>()
    newValue += value.orEmpty()
    newValue.addAll(data.orEmpty())
    value = newValue

}

