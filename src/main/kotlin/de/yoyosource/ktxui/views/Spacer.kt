package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import kotlin.reflect.KProperty0

fun OrientedViewContainer.Spacer() {
    +de.yoyosource.ktxui.views.Spacer()
}

fun OrientedViewContainer.Spacer(length: Int): Spacer {
    return +Spacer(ViewOption(length))
}

fun OrientedViewContainer.Spacer(length: KProperty0<Int>): Spacer {
    return (+Spacer(ViewOption(0))).length(length)
}

class Spacer internal constructor(private val length: ViewOption<Int>? = null) : View() {

    fun length(length: Int) = apply {
        this.length?.set(this, length)
    }

    fun length(length: KProperty0<Int>) = apply {
        this.length?.set(this, length)
    }

    override fun size(drawableData: DrawableData): Element {
        if (length == null) {
            return Element(0, 0)
        }
        return if ((parent as OrientedViewContainer).orientation == Orientation.HORIZONTAL) {
            Element(length.get(), 0)
        } else {
            Element(0, length.get())
        }
    }

    override fun size(drawableData: DrawableData, screenSize: Element, viewState: ViewState) {
        if (length == null) {
            val current = screenSize.copy()
            if ((parent as OrientedViewContainer).orientation == Orientation.HORIZONTAL) {
                current.y = 0
            } else {
                current.x = 0
            }
            viewState.sizeMap[this] = current
        } else {
            val current = Element(0, 0)
            if ((parent as OrientedViewContainer).orientation == Orientation.HORIZONTAL) {
                current.x = length.get()
            } else {
                current.y = length.get()
            }
            viewState.sizeMap[this] = current
        }
    }

    override fun spacers(orientation: Orientation): Int {
        return if ((parent as OrientedViewContainer).orientation == orientation) {
            1
        } else {
            0
        }
    }

    override fun draw(drawable: Drawable, viewState: ViewState, location: Element) {
        location + viewState.sizeMap[this]!!
    }
}