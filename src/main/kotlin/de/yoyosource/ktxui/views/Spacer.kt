package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*

fun OrientedViewContainer.Spacer(): Spacer {
    return +de.yoyosource.ktxui.views.Spacer()
}

class Spacer internal constructor() : View() {

    override fun size(drawableData: DrawableData): Element {
        return Element(0, 0)
    }

    override fun size(drawableData: DrawableData, screenSize: Element, viewState: ViewState) {
        val current = screenSize.copy()
        if ((parent as OrientedViewContainer).orientation == Orientation.HORIZONTAL) {
            current.y = 0
        } else {
            current.x = 0
        }
        viewState.sizeMap[this] = current
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