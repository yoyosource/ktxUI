package de.yoyosource.ktxui.api.views.shapes

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.api.data.Black
import de.yoyosource.ktxui.api.data.Color
import de.yoyosource.ktxui.api.protocols.BorderProtocol
import de.yoyosource.ktxui.api.protocols.ForegroundColorProtocol
import de.yoyosource.ktxui.api.protocols.RadiusProtocol
import de.yoyosource.ktxui.api.protocols.getForegroundColor
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.ViewOption
import de.yoyosource.ktxui.utils.ViewState

fun ViewContainer.Circle(): Circle<*> {
    return +CircleImpl()
}
sealed interface Circle<S> : ViewProtocol<S, Circle<S>>, ForegroundColorProtocol<Circle<S>>, BorderProtocol<S, Circle<S>>, RadiusProtocol<S, Circle<S>> where S : ViewBase

private class CircleImpl : DrawableView(), Circle<CircleImpl> {

    override val selfView: CircleImpl = this
    override val selfAPI: Circle<CircleImpl> = this

    override val foregroundColor: ViewOption<Color> = ViewOption(Black)

    override val borderColor: ViewOption<Color> = ViewOption(Black)
    override val borderWidth: ViewOption<Int> = ViewOption(0)

    override val radius: ViewOption<Int> = ViewOption(0)

    override fun size(drawableData: DrawableData): Element {
        return Element(radius.get() * 2, radius.get() * 2)
    }

    override fun draw(drawable: Drawable, viewState: ViewState) {
        val (location, size) = viewState[this]
        drawable.setColor(getForegroundColor())
        drawable.fillCircle(location, radius.get())
        drawable.drawBorder(this, location, size)
    }
}
