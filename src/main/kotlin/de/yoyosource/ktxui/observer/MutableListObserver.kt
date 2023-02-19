package de.yoyosource.ktxui.observer

fun <T> MutableList<T>.asObservable(): MutableListObservable<T> {
    return MutableListObservable(this)
}

class MutableListObservable<E>(private val inner: MutableList<E>) : MutableList<E> {

    private val observers = mutableListOf<(List<E>) -> Unit>()

    private fun notifyObservers() {
        observers.forEach { it(inner) }
    }

    fun addObserver(observer: (List<E>) -> Unit) {
        observers.add(observer)
    }

    fun removeObserver(observer: (List<E>) -> Unit) {
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