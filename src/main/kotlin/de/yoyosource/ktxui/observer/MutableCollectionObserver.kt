package de.yoyosource.ktxui.observer

interface MutableCollectionObserver<E> : MutableCollection<E> {
    fun addObserver(observer: (Collection<E>) -> Unit)
    fun removeObserver(observer: (Collection<E>) -> Unit)
}