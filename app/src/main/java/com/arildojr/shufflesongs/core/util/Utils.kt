package com.arildojr.shufflesongs.core.util

fun combine(lists: Array<out List<*>>): List<Any> = mutableListOf<Any>().also {
    combine(it, lists.map(List<*>::iterator))
}

private tailrec fun combine(targetList: MutableList<Any>, iterators: List<Iterator<*>>) {
    iterators.asSequence()
        .filter(Iterator<*>::hasNext)
        .mapNotNull(Iterator<*>::next)
        .forEach { targetList += it }
    if (iterators.asSequence().any(Iterator<*>::hasNext))
        combine(targetList, iterators)
}

inline fun <reified T> tryCast(instance: Any?, block: T.() -> Unit) {
    if (instance is T) {
        block(instance)
    }
}