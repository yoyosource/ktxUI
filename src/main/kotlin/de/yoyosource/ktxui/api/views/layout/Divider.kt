package de.yoyosource.ktxui.api.views.layout

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.api.data.Color
import de.yoyosource.ktxui.api.data.Black
import de.yoyosource.ktxui.api.protocols.ForegroundColorProtocol
import de.yoyosource.ktxui.api.protocols.getForegroundColor
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.Orientation
import de.yoyosource.ktxui.utils.ViewOption
import de.yoyosource.ktxui.utils.ViewState

fun OrientedViewContainer.Divider(): Divider<*> {
    return +DividerImpl()
}

sealed interface Divider<S> : ViewProtocol<S, Divider<S>>, ForegroundColorProtocol<Divider<S>> where S : ViewBase

private class DividerImpl : DrawableView(), Divider<DividerImpl> {

    override val selfView: DividerImpl = this
    override val selfAPI: Divider<DividerImpl> = this

    override val foregroundColor: ViewOption<Color> = ViewOption(Black)

    override fun size(drawableData: DrawableData): Element {
        return if ((parent as OrientedViewContainer).orientation == Orientation.VERTICAL) {
            Element(0, 1)
        } else {
            Element(1, 0)
        }
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        val current = screenSize.copy()
        if ((parent as OrientedViewContainer).orientation == Orientation.VERTICAL) {
            current.y = 1
        } else {
            current.x = 1
        }
        viewState.set(this, location.copy(), current)
        location + current
    }

    override fun draw(drawable: Drawable, viewState: ViewState) {
        val (location, size) = viewState[this]
        drawable.setColor(getForegroundColor())
        drawable.fillRectangle(location, size)
    }
}