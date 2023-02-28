package de.yoyosource.ktxui.api.views.shapes

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.api.data.Black
import de.yoyosource.ktxui.api.data.Color
import de.yoyosource.ktxui.api.protocols.BorderProtocol
import de.yoyosource.ktxui.api.protocols.ForegroundColorProtocol
import de.yoyosource.ktxui.api.protocols.WidthHeightProtocol
import de.yoyosource.ktxui.api.protocols.getForegroundColor
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ViewOption
import de.yoyosource.ktxui.utils.ViewState

fun ViewContainer.Rectangle(): Rectangle<*> {
    return +RectangleImpl()
}

sealed interface Rectangle<S> : ViewProtocol<S, Rectangle<S>>, ForegroundColorProtocol<S, Rectangle<S>>, BorderProtocol<S, Rectangle<S>>, WidthHeightProtocol<S, Rectangle<S>> where S : ViewBase

private class RectangleImpl : DrawableView(), Rectangle<RectangleImpl> {

    override val selfView: RectangleImpl = this
    override val selfAPI: Rectangle<RectangleImpl> = this

    override val borderColor: ViewOption<Color> = ViewOption(Black)
    override val borderWidth: ViewOption<Int> = ViewOption(0)

    override val foregroundColor: ViewOption<Color> = ViewOption(Black)

    override val width: ViewOption<Int> = ViewOption(0)
    override val height: ViewOption<Int> = ViewOption(0)

    override fun size(drawableData: DrawableData): Element {
        return Element(width.get(), height.get())
    }

    override fun draw(drawable: Drawable, viewState: ViewState) {
        val (location, size) = viewState[this]
        drawable.setColor(getForegroundColor())
        drawable.fillRectangle(location, size)
        drawable.drawBorder(this, location, size)
    }
}
