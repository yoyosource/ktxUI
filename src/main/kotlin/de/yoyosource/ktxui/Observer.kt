package de.yoyosource.ktxui

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

interface Observer<T : Any> : ReadWriteProperty<Any?, T> {
    fun addObserver(observer: (T) -> Unit)
    fun removeObserver(observer: (T) -> Unit)

    companion object {

        operator fun <T : Any> invoke(): Observer<T> {
            return ValueObserver()
        }

        operator fun <T : Any> invoke(value: T): Observer<T> {
            return ValueObserver(value)
        }

        operator fun <S : Any, T : Any> invoke(source: KProperty0<S>, delegate: (S) -> T): Observer<T> {
            return DelegateObserver(source, delegate)
        }
    }
}

abstract class ObserverImpl<T : Any> : Observer<T> {
    private var observers = mutableSetOf<(T) -> Unit>()

    internal fun notifyObservers(value: T) {
        observers.forEach { it(value) }
    }

    override fun addObserver(observer: (T) -> Unit) {
        observers.add(observer)
    }

    override fun removeObserver(observer: (T) -> Unit) {
        observers.remove(observer)
    }
}

class ValueObserver<T : Any> : ObserverImpl<T> {
    private lateinit var value: T

    constructor()

    constructor(value: T) {
        this.value = value
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
        notifyObservers(value)
    }
}

class DelegateObserver<S : Any, T : Any> : ObserverImpl<T> {
    private lateinit var value: T

    constructor(source: KProperty0<S>, delegate: (S) -> T) {
        value = delegate(source.get())
        val sourceProperty = source.apply {
            isAccessible = true
        }.getDelegate()
        if (sourceProperty !is Observer<*>) {
            throw IllegalArgumentException("Source property must be an observer")
        }

        sourceProperty.addObserver {
            this.value = delegate(it as S)
            notifyObservers(this.value)
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        throw UnsupportedOperationException("Cannot set value of delegate observer")
    }
}
