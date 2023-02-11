package de.yoyosource.ktxui

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Observer<T : Any> : ReadWriteProperty<Any?, T> {
    private lateinit var value: T

    constructor(value: T) {
        this.value = value
    }

    constructor()

    private var observers = mutableSetOf<(T) -> Unit>()

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
        observers.forEach { it(value) }
    }

    fun addObserver(observer: (T) -> Unit) {
        observers.add(observer)
    }

    fun removeObserver(observer: (T) -> Unit) {
        observers.remove(observer)
    }
}
