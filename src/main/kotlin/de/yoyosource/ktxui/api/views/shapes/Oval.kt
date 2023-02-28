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

fun ViewContainer.Oval(): Oval<*> {
    return +OvalImpl()
}

sealed interface Oval<S> : ViewProtocol<S, Oval<S>>, ForegroundColorProtocol<S, Oval<S>>, BorderProtocol<S, Oval<S>>, WidthHeightProtocol<S, Oval<S>> where S : ViewBase

private class OvalImpl : DrawableView(), Oval<OvalImpl> {

    override val selfView: OvalImpl = this
    override val selfAPI: Oval<OvalImpl> = this

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
        drawable.fillOval(location, width.get(), height.get())
        drawable.drawBorder(this, location, size)
    }
}
