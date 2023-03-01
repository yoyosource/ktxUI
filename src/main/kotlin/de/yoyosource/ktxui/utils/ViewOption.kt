package de.yoyosource.ktxui.utils

import de.yoyosource.ktxui.Observer
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

class ViewOption<T : Any>(value: T) {

    private var removeObserver: () -> Unit = {}
    private var value: () -> T = { value }

    fun get(): T {
        return value()
    }

    fun set(redraw: () -> Unit, value: T) {
        removeObserver()
        removeObserver = {}
        this.value = { value }
    }

    fun set(redraw: () -> Unit, value: KProperty0<T>) {
        if (value.isLateinit) throw IllegalArgumentException("Lateinit properties are not supported")
        removeObserver()

        val delegate = value.apply {
            this.isAccessible = true
        }.getDelegate()
        this@ViewOption.value = { value.get() }
        if (delegate is Observer<*>) {
            val observer: (Any) -> Unit = { redraw() }
            delegate.addObserver(observer)
            removeObserver = { delegate.removeObserver(observer) }
        }
    }

    fun set(redraw: () -> Unit, value: Either<T, KProperty0<T>>) {
        return when (value) {
            is Either.Left -> set(redraw, value.value)
            is Either.Right -> set(redraw, value.value)
        }
    }
}