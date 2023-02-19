package de.yoyosource.ktxui.observer

interface MutableCollectionObservable<E, L : MutableCollection<E>> {
    fun addObserver(observer: (L) -> Unit)
    fun removeObserver(observer: (L) -> Unit)
}