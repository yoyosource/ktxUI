package de.yoyosource.ktxui.utils

import de.yoyosource.ktxui.DrawableView
import de.yoyosource.ktxui.Observer
import de.yoyosource.ktxui.ViewBase
import de.yoyosource.ktxui.views.events.*
import kotlin.math.roundToInt
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

fun <T, V: ViewBase> observableInit(property : KProperty0<T>, viewCreator: (() -> T) -> V): V {
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
    private var order: MutableList<DrawableView> = mutableListOf()
    private var positions: MutableMap<DrawableView, Element> = mutableMapOf()
    private var sizes: MutableMap<DrawableView, Element> = mutableMapOf()
    private var eventPositions: MutableMap<Event, Element> = mutableMapOf()
    private var keyboards: MutableSet<Keyboard> = mutableSetOf()

    fun set(view: DrawableView, location: Element, size: Element) {
        if (!(positions.containsKey(view) && sizes.containsKey(view))) {
            order.add(view)
        }
        positions[view] = location.copy()
        this.sizes[view] = size.copy()
    }

    fun set(view: Event, location: Element) {
        eventPositions[view] = location.copy()
    }

    fun set(keyboard: Keyboard) {
        keyboards.add(keyboard)
    }

    fun forEach(width: Int, height: Int, action: (DrawableView) -> Unit) {
        order.forEach {
            val (position, size) = get(it)
            if (position.x >= width || position.y >= height || position.x + size.x < 0 || position.y + size.y < 0) {
                return@forEach
            }
            action(it)
        }
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
        var view = view as ViewBase
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
            val (viewPosX, viewPosY) = eventPositions[it]!!
            it.click(viewPosX, viewPosY, x - viewPosX, y - viewPosY, x, y)
        }
    }

    fun hover(view: DrawableView, x: Int, y: Int) {
        walk<Hover>(view) {
            val (viewPosX, viewPosY) = eventPositions[it]!!
            it.hover(viewPosX, viewPosY, x - viewPosX, y - viewPosY, x, y)
        }
    }

    fun drag(view: DrawableView, x: Int, y: Int) {
        walk<Drag>(view) {
            val (viewPosX, viewPosY) = eventPositions[it]!!
            it.drag(viewPosX, viewPosY, x - viewPosX, y - viewPosY, x, y)
        }
    }

    fun scroll(view: DrawableView, x: Int, y: Int, wheelRotation: Double) {
        walk<Scroll>(view) {
            val (viewPosX, viewPosY) = eventPositions[it]!!
            it.scroll(viewPosX, viewPosY, x - viewPosX, y - viewPosY, x, y, wheelRotation)
        }
    }

    fun press(keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>) {
        keyboards.forEach { it.press(keyCode, keyChar, modifier) }
    }

    fun release(keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>) {
        keyboards.forEach { it.release(keyCode, keyChar, modifier) }
    }

    fun type(keyCode: Int, keyChar: Char, modifier: Set<ModifierKey>) {
        keyboards.forEach { it.type(keyCode, keyChar, modifier) }
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