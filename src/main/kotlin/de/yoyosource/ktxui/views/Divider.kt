package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.Orientation
import de.yoyosource.ktxui.utils.ViewOption
import de.yoyosource.ktxui.utils.ViewState
import java.awt.Color
import kotlin.reflect.KProperty0

fun OrientedViewContainer.Divider(): Divider {
    return +DividerImpl()
}

fun OrientedViewContainer.Divider(length: Int): Divider {
    return +DividerImpl(ViewOption(length))
}

fun OrientedViewContainer.Divider(length: KProperty0<Int>): Divider {
    return (+DividerImpl(ViewOption(0))).length(length)
}

sealed interface Divider: ViewAPI {
    fun length(length: Int): Divider
    fun length(length: KProperty0<Int>): Divider
    fun color(color: Color): Divider
    fun color(color: KProperty0<Color>): Divider
    fun thickness(width: Int): Divider
    fun thickness(width: KProperty0<Int>): Divider
}

private class DividerImpl constructor(private val length: ViewOption<Int>? = null) : ViewElement(), Divider {

    private var color = ViewOption(Color.BLACK)
    private var thickness = ViewOption(1)

    override fun length(length: Int) = apply {
        if (this.length == null) throw IllegalArgumentException("Length is not set")
        this.length.set(this, length)
    }

    override fun length(length: KProperty0<Int>) = apply {
        if (this.length == null) throw IllegalArgumentException("Length is not set")
        this.length.set(this, length)
    }

    override fun color(color: Color) = apply {
        this.color.set(this, color)
    }

    override fun color(color: KProperty0<Color>) = apply {
        this.color.set(this, color)
    }

    override fun thickness(width: Int) = apply {
        this.thickness.set(this, width)
    }

    override fun thickness(width: KProperty0<Int>) = apply {
        this.thickness.set(this, width)
    }

    override fun size(drawableData: DrawableData): Element {
        if (length == null) {
            return if ((parent as OrientedViewContainer).orientation == Orientation.VERTICAL) {
                Element(0, thickness.get())
            } else {
                Element(thickness.get(), 0)
            }
        }
        return if ((parent as OrientedViewContainer).orientation == Orientation.VERTICAL) {
            Element(length.get(), thickness.get())
        } else {
            Element(thickness.get(), length.get())
        }
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        if (length == null) {
            val current = screenSize.copy()
            if ((parent as OrientedViewContainer).orientation == Orientation.VERTICAL) {
                current.y = thickness.get()
            } else {
                current.x = thickness.get()
            }
            viewState.set(this, location.copy(), current)
        } else {
            val current = Element(0, 0)
            if ((parent as OrientedViewContainer).orientation == Orientation.VERTICAL) {
                current.x = length.get()
                current.y = thickness.get()
            } else {
                current.x = thickness.get()
                current.y = length.get()
            }
            viewState.set(this, location.copy(), current)
        }
        location + viewState[this].second
    }

    override fun draw(drawable: Drawable, viewState: ViewState) {
        val (location, size) = viewState[this]
        drawable.drawRectangle(location, size, color.get())
    }
}