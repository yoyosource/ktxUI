package de.yoyosource.ktxui.api.views.shapes

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.api.data.Black
import de.yoyosource.ktxui.api.data.Color
import de.yoyosource.ktxui.api.protocols.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ViewOption
import de.yoyosource.ktxui.utils.ViewState

fun ViewContainer.RoundedRectangle(): RoundedRectangle<*> {
    return +RoundedRectangleImpl()
}

sealed interface RoundedRectangle<S> : ViewProtocol<S, RoundedRectangle<S>>, ForegroundColorProtocol<S, RoundedRectangle<S>>, BorderProtocol<S, RoundedRectangle<S>>, WidthHeightProtocol<S, RoundedRectangle<S>>, CornerRadiusProtocol<S, RoundedRectangle<S>> where S : ViewBase

private class RoundedRectangleImpl : DrawableView(), RoundedRectangle<RoundedRectangleImpl> {

    override val selfView: RoundedRectangleImpl = this
    override val selfAPI: RoundedRectangle<RoundedRectangleImpl> = this

    override val borderColor: ViewOption<Color> = ViewOption(Black)
    override val borderWidth: ViewOption<Int> = ViewOption(0)

    override val arcWidth: ViewOption<Int> = ViewOption(0)
    override val arcHeight: ViewOption<Int> = ViewOption(0)

    override val foregroundColor: ViewOption<Color> = ViewOption(Black)

    override val width: ViewOption<Int> = ViewOption(0)
    override val height: ViewOption<Int> = ViewOption(0)

    override fun size(drawableData: DrawableData): Element {
        return Element(width.get(), height.get())
    }

    override fun draw(drawable: Drawable, viewState: ViewState) {
        val (location, size) = viewState[this]
        drawable.setColor(getForegroundColor())
        drawable.fillRoundedRectangle(location, size, arcWidth.get(), arcHeight.get())
        drawable.drawBorder(this, location, size)
    }
}
