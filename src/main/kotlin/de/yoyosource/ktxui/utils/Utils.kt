package de.yoyosource.ktxui.utils

import de.yoyosource.ktxui.DrawableView
import de.yoyosource.ktxui.Observer
import de.yoyosource.ktxui.View
import de.yoyosource.ktxui.views.events.Button
import de.yoyosource.ktxui.views.events.Drag
import de.yoyosource.ktxui.views.events.Hover
import de.yoyosource.ktxui.views.events.Scroll
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

class ViewState {
    var order: MutableList<DrawableView> = mutableListOf()
    var positions: MutableMap<DrawableView, Element> = mutableMapOf()
    var sizes: MutableMap<DrawableView, Element> = mutableMapOf()

    fun set(view: DrawableView, location: Element, size: Element) {
        if (!(positions.containsKey(view) && sizes.containsKey(view))) {
            order.add(view)
        }
        positions[view] = location.copy()
        this.sizes[view] = size.copy()
    }

    operator fun get(view: DrawableView): Pair<Element, Element> {
        return Pair(positions[view]!!, sizes[view]!!)
    }

    operator fun get(x: Int, y: Int): DrawableView? {
        order.asReversed().forEach {
            val (location, size) = this[it]
            if (x in location.x until location.x + size.x && y in location.y until location.y + size.y) {
                return it
            }
        }
        return null
    }

    private inline fun <reified T> walk(view: DrawableView, action: (T) -> Unit) {
        var view = view as View
        while (view.parent != null) {
            view = view.parent!!
            if (view is T) {
                action(view)
                return
            }
        }
    }

    fun click(view: DrawableView, x: Int, y: Int) {
        walk<Button>(view) {
            val (viewPosX, viewPosY) = positions[it as DrawableView]!!
            it.click(viewPosX, viewPosY, x - viewPosX, y - viewPosY, x, y)
        }
    }

    fun hover(view: DrawableView, x: Int, y: Int) {
        walk<Hover>(view) {
            val (viewPosX, viewPosY) = positions[it as DrawableView]!!
            it.hover(viewPosX, viewPosY, x - viewPosX, y - viewPosY, x, y)
        }
    }

    fun drag(view: DrawableView, x: Int, y: Int) {
        walk<Drag>(view) {
            val (viewPosX, viewPosY) = positions[it as DrawableView]!!
            it.drag(viewPosX, viewPosY, x - viewPosX, y - viewPosY, x, y)
        }
    }

    fun scroll(view: DrawableView, x: Int, y: Int, wheelRotation: Double) {
        walk<Scroll>(view) {
            val (viewPosX, viewPosY) = positions[it as DrawableView]!!
            it.scroll(viewPosX, viewPosY, x - viewPosX, y - viewPosY, x, y, wheelRotation)
        }
    }
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