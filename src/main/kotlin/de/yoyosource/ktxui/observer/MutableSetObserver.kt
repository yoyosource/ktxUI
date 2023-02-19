package de.yoyosource.ktxui.observer

fun <T> MutableSet<T>.asObservable(): MutableSetObservable<T> {
    return MutableSetObservable(this)
}

class MutableSetObservable<E>(private val inner: MutableSet<E>) : MutableSet<E> {

    private val observers = mutableListOf<(Set<E>) -> Unit>()

    private fun notifyObservers() {
        observers.forEach { it(inner) }
    }

    fun addObserver(observer: (Set<E>) -> Unit) {
        observers.add(observer)
    }

    fun removeObserver(observer: (Set<E>) -> Unit) {
        observers.remove(observer)
    }

    override fun add(element: E) = inner.add(element).also { notifyObservers() }

    override fun addAll(elements: Collection<E>) = inner.addAll(elements).also { notifyObservers() }

    override fun clear() = inner.clear().also { notifyObservers() }

    override fun iterator() = inner.iterator()

    override fun remove(element: E) = inner.remove(element).also { notifyObservers() }

    override fun removeAll(elements: Collection<E>) = inner.removeAll(elements).also { notifyObservers() }

    override fun retainAll(elements: Collection<E>) = inner.retainAll(elements).also { notifyObservers() }

    override val size: Int
        get() = inner.size

    override fun contains(element: E) = inner.contains(element)

    override fun containsAll(elements: Collection<E>) = inner.containsAll(elements)

    override fun isEmpty() = inner.isEmpty()
}