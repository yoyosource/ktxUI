package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import java.awt.Color
import kotlin.reflect.KProperty0

fun OrientedViewContainer.Divider(): Divider {
    return +de.yoyosource.ktxui.views.Divider()
}

fun OrientedViewContainer.Divider(length: Int): Divider {
    return +Divider(ViewOption(length))
}

fun OrientedViewContainer.Divider(length: KProperty0<Int>): Divider {
    return (+Divider(ViewOption(0))).length(length)
}

class Divider internal constructor(private val length: ViewOption<Int>? = null) : ViewElement() {

    private var color = ViewOption(Color.BLACK)
    private var width = ViewOption(1)

    fun length(length: Int) = apply {
        if (this.length == null) throw IllegalArgumentException("Length is not set")
        this.length.set(this, length)
    }

    fun length(length: KProperty0<Int>) = apply {
        if (this.length == null) throw IllegalArgumentException("Length is not set")
        this.length.set(this, length)
    }

    fun color(color: Color) = apply {
        this.color.set(this, color)
    }

    fun color(color: KProperty0<Color>) = apply {
        this.color.set(this, color)
    }

    fun thickness(width: Int) = apply {
        this.width.set(this, width)
    }

    fun thickness(width: KProperty0<Int>) = apply {
        this.width.set(this, width)
    }

    override fun size(drawableData: DrawableData): Element {
        if (length == null) {
            return if ((parent as OrientedViewContainer).orientation == Orientation.VERTICAL) {
                Element(0, width.get())
            } else {
                Element(width.get(), 0)
            }
        }
        return if ((parent as OrientedViewContainer).orientation == Orientation.VERTICAL) {
            Element(length.get(), width.get())
        } else {
            Element(width.get(), length.get())
        }
    }

    override fun size(drawableData: DrawableData, screenSize: Element, viewState: ViewState) {
        if (length == null) {
            val current = screenSize.copy()
            if ((parent as OrientedViewContainer).orientation == Orientation.VERTICAL) {
                current.x = width.get()
            } else {
                current.y = width.get()
            }
            viewState.sizeMap[this] = current
        } else {
            val current = Element(0, 0)
            if ((parent as OrientedViewContainer).orientation == Orientation.VERTICAL) {
                current.x = length.get()
                current.y = width.get()
            } else {
                current.x = width.get()
                current.y = length.get()
            }
            viewState.sizeMap[this] = current
        }
    }

    override fun draw(drawable: Drawable, viewState: ViewState, location: Element) {
        val size = viewState.sizeMap[this]!!
        drawable.drawRectangle(location, size, color.get())
        drawable.drawRectangle(location, Element(1, 1), Color.CYAN)
        location + size
    }
}