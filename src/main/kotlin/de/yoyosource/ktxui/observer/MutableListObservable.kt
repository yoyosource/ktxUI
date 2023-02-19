package de.yoyosource.ktxui.observer

fun <E, L : MutableList<E>> L.asObservable(): MutableListObservable<E, L> {
    return MutableListObservable(this)
}

class MutableListObservable<E, L : MutableList<E>>(private val inner: L) : MutableList<E>, MutableCollectionObservable<E, L> {

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

    override val size: Int
        get() = inner.size

    override fun contains(element: E) = inner.contains(element)

    override fun containsAll(elements: Collection<E>) = inner.containsAll(elements)

    override operator fun get(index: Int) = inner[index]

    override fun indexOf(element: E) = inner.indexOf(element)

    override fun isEmpty() = inner.isEmpty()

    override fun iterator() = inner.iterator()

    override fun lastIndexOf(element: E) = inner.lastIndexOf(element)

    override fun add(element: E) = inner.add(element).also { notifyObservers() }

    override fun add(index: Int, element: E) = inner.add(index, element).also { notifyObservers() }

    override fun addAll(index: Int, elements: Collection<E>) = inner.addAll(index, elements).also { notifyObservers() }

    override fun addAll(elements: Collection<E>) = inner.addAll(elements).also { notifyObservers() }

    override fun clear() = inner.clear().also { notifyObservers() }

    override fun listIterator() = inner.listIterator()

    override fun listIterator(index: Int) = inner.listIterator(index)

    override fun remove(element: E) = inner.remove(element).also { notifyObservers() }

    override fun removeAll(elements: Collection<E>) = inner.removeAll(elements).also { notifyObservers() }

    override fun removeAt(index: Int) = inner.removeAt(index).also { notifyObservers() }

    override fun retainAll(elements: Collection<E>) = inner.retainAll(elements).also { notifyObservers() }

    override operator fun set(index: Int, element: E) = inner.set(index, element).also { notifyObservers() }

    override fun subList(fromIndex: Int, toIndex: Int) = inner.subList(fromIndex, toIndex)
}