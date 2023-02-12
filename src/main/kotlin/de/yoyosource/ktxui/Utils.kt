package de.yoyosource.ktxui

import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

fun <T, V: View> observableInit(property : KProperty0<T>, viewCreator: (() -> T) -> V): V {
    if (property.isLateinit) throw IllegalArgumentException("Lateinit properties are not supported")
    val delegate = property.apply {
        this.isAccessible = true
    }.getDelegate()
    val view = viewCreator { property.get() }
    if (delegate is Observer<*>) {
        delegate.addObserver {
            view.redraw()
        }
    }
    return view
}

enum class Orientation {
    HORIZONTAL,
    VERTICAL
}

enum class Side {
    TOP,
    BOTTOM,
    LEFT,
    RIGHT
}

class ViewState {
    var sizeMap: MutableMap<View, Element> = IdentityHashMap()
}

class Element(x: Int, y: Int) {

    constructor(x: Double, y: Double) : this(x.roundToInt(), y.roundToInt())

    var x: Int = x
    var y: Int = y

    operator fun component1() = x
    operator fun component2() = y

    operator fun plus(other: Element): Element {
        x += other.x
        y += other.y
        return this
    }

    operator fun minus(other: Element): Element {
        x -= other.x
        y -= other.y
        return this
    }

    operator fun unaryMinus(): Element {
        x = -x
        y = -y
        return this
    }

    operator fun times(other: Element): Element {
        x *= other.x
        y *= other.y
        return this
    }

    operator fun times(other: Int): Element {
        x *= other
        y *= other
        return this
    }

    operator fun div(other: Element): Element {
        x /= other.x
        y /= other.y
        return this
    }

    operator fun div(other: Int): Element {
        x /= other
        y /= other
        return this
    }

    fun copy(width: Int = this.x, height: Int = this.y): Element {
        return Element(width, height)
    }

    fun abs(): Element {
        x = x.absoluteValue
        y = y.absoluteValue
        return this
    }

    override fun toString(): String {
        return "Size(x=$x, y=$y)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Element) return false

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }
}

class SpacerCalculation(size: Int, count: Int) {
    private val delta: Float = if (count > 0) size.toFloat() / count else 0f
    private var current: Float = 0f

    fun next(): Int {
        val temp = current
        current += delta
        return current.toInt() - temp.toInt()
    }
}