package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import kotlin.reflect.KProperty0

fun OrientedViewContainer.Spacer() {
    +SpacerImpl()
}

fun OrientedViewContainer.Spacer(length: Int): Spacer {
    return +SpacerImpl(ViewOption(length))
}

fun OrientedViewContainer.Spacer(length: KProperty0<Int>): Spacer {
    return (+SpacerImpl(ViewOption(0))).length(length)
}

sealed interface Spacer: ViewAPI {
    fun length(length: Int): Spacer
    fun length(length: KProperty0<Int>): Spacer
}

private class SpacerImpl constructor(private val length: ViewOption<Int>? = null) : View(), Spacer {

    override fun length(length: Int) = apply {
        this.length?.set(this, length)
    }

    override fun length(length: KProperty0<Int>) = apply {
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

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        if (length == null) {
            val current = screenSize.copy()
            if ((parent as OrientedViewContainer).orientation == Orientation.HORIZONTAL) {
                current.y = 0
            } else {
                current.x = 0
            }
            drawableData.debug(DebugMode.SIZE, "$this $current ${(parent as OrientedViewContainer).orientation}")
            location + current
        } else {
            val current = Element(0, 0)
            if ((parent as OrientedViewContainer).orientation == Orientation.HORIZONTAL) {
                current.x = length.get()
            } else {
                current.y = length.get()
            }
            drawableData.debug(DebugMode.SIZE, "$this $current ${(parent as OrientedViewContainer).orientation}")
            location + current
        }
    }

    override fun spacers(orientation: Orientation): Int {
        return if ((parent as OrientedViewContainer).orientation == orientation) {
            1
        } else {
            0
        }
    }
}