package de.yoyosource.ktxui.api.views.layout

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.api.data.Color
import de.yoyosource.ktxui.api.data.black
import de.yoyosource.ktxui.api.protocols.ForegroundColorAPI
import de.yoyosource.ktxui.api.protocols.getForegroundColor
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.Orientation
import de.yoyosource.ktxui.utils.ViewOption
import de.yoyosource.ktxui.utils.ViewState

fun OrientedViewContainer.Divider(): Divider<*> {
    return +DividerImpl()
}

sealed interface Divider<S> : DividerAPI<S, Divider<S>> where S : ViewBase

sealed interface DividerAPI<S, A> : ViewAPI<S, A>, ForegroundColorAPI<S, A> where S : ViewBase, A : DividerAPI<S, A>

private class DividerImpl : DrawableView(), Divider<DividerImpl> {

    override val selfView: DividerImpl = this
    override val selfAPI: Divider<DividerImpl> = this

    override val foregroundColor: ViewOption<Color> = ViewOption(black)

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