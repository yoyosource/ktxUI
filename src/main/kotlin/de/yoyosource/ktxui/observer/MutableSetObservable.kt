package de.yoyosource.ktxui.observer

fun <E, L : MutableSet<E>> L.asObservable(): MutableSetObservable<E, L> {
    return MutableSetObservable(this)
}

class MutableSetObservable<E, L : MutableSet<E>>(private val inner: L) : MutableSet<E>, MutableCollectionObservable<E, L> {

    private val observers = mutableListOf<(L) -> Unit>()

    private fun notifyObservers() {
        observers.forEach { it(inner) }
    }

    override fun addObserver(observer: (L) -> Unit) {
        observers.add(observer)
    }

    override fun removeObserver(observer: (L) -> Unit) {
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