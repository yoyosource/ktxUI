package de.yoyosource.ktxui.utils

import de.yoyosource.ktxui.Observer
import de.yoyosource.ktxui.ViewBase
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

class ViewOption<T : Any>(value: T) {

    private var removeObserver: () -> Unit = {}
    private var value: () -> T = { value }

    fun get(): T {
        return value()
    }

    fun <V : ViewBase> set(self: V, value: T): V {
        removeObserver()
        removeObserver = {}
        this.value = { value }
        return self
    }

    fun <V : ViewBase> set(self: V, value: KProperty0<T>): V {
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

    fun <V: ViewBase> set(self: V, value: Either<T, KProperty0<T>>): V {
        return when (value) {
            is Either.Left -> set(self, value.value)
            is Either.Right -> set(self, value.value)
            else -> throw IllegalArgumentException("Either must be either Left or Right")
        }
    }
}