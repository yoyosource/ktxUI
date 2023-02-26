package de.yoyosource.ktxui.api.views.layout

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.Orientation
import de.yoyosource.ktxui.utils.ViewOption
import de.yoyosource.ktxui.utils.ViewState
import kotlin.reflect.KProperty0

fun OrientedViewContainer.Spacer(): Spacer<*> {
    return +SpacerImpl(null)
}

fun OrientedViewContainer.Spacer(minLength: Int): Spacer<*> {
    return +SpacerImpl(ViewOption(minLength))
}

fun OrientedViewContainer.Spacer(minLength: KProperty0<Int>): Spacer<*> {
    val length = ViewOption(0)
    val spacer = +SpacerImpl(length)
    length.set(spacer, minLength)
    return spacer
}

sealed interface Spacer<S> : SpacerAPI<S, Spacer<S>> where S : ViewBase

sealed interface SpacerAPI<S, A> : ViewAPI<S, A> where S : ViewBase, A : SpacerAPI<S, A>

private class SpacerImpl constructor(private val length: ViewOption<Int>? = null) : ViewBase(), Spacer<SpacerImpl> {

    override val selfView: SpacerImpl = this
    override val selfAPI: Spacer<SpacerImpl> = this

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
            location + current
        } else {
            val current = Element(0, 0)
            if ((parent as OrientedViewContainer).orientation == Orientation.HORIZONTAL) {
                current.x = length.get()
            } else {
                current.y = length.get()
            }
            location + current
        }
    }

    override fun spacers(orientation: Orientation): Int {
        if (length != null) {
            return 0
        }
        return if ((parent as OrientedViewContainer).orientation == orientation) {
            1
        } else {
            0
        }
    }
}