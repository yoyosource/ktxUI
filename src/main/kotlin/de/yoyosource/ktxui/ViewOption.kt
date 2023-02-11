package de.yoyosource.ktxui

import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

class ViewOption<T : Any>(value: T) {

    private var removeObserver: () -> Unit = {}
    private var value: () -> T = { value }

    fun get(): T {
        return value()
    }

    fun <V : View> set(self: V, value: T): V {
        removeObserver()
        removeObserver = {}
        this.value = { value }
        return self
    }

    fun <V : View> set(self: V, value: KProperty0<T>): V {
        if (value.isLateinit) throw IllegalArgumentException("Lateinit properties are not supported")
        removeObserver()

        val delegate = value.apply {
            this.isAccessible = true
        }.getDelegate()
        this@ViewOption.value = { value.get() }
        if (delegate is Observer<*>) {
            val observer: (Any) -> Unit = {
                self.redraw()
            }
            delegate.addObserver(observer)
            removeObserver = { delegate.removeObserver(observer) }
        }
        return self
    }
}