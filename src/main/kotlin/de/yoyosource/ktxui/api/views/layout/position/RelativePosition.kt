package de.yoyosource.ktxui.api.views.layout.position

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.*
import kotlin.reflect.KProperty0

fun ViewContainer.RelativePosition(x: Int, y: Int, builder: SingleViewBuilder): RelativePosition<*> {
    return (+RelativePositionImpl(ViewOption(x), ViewOption(y))).apply(builder)
}

fun ViewContainer.RelativePosition(x: KProperty0<Int>, y: Int, builder: SingleViewBuilder): RelativePosition<*> {
    val _x = ViewOption(0)
    return (+RelativePositionImpl(_x, ViewOption(y))).apply(builder).apply {
        _x.set(redraw, x)
    }
}

fun ViewContainer.RelativePosition(x: Int, y: KProperty0<Int>, builder: SingleViewBuilder): RelativePosition<*> {
    val _y = ViewOption(0)
    return (+RelativePositionImpl(ViewOption(x), _y)).apply(builder).apply {
        _y.set(redraw, y)
    }
}

fun ViewContainer.RelativePosition(x: KProperty0<Int>, y: KProperty0<Int>, builder: SingleViewBuilder): RelativePosition<*> {
    val _x = ViewOption(0)
    val _y = ViewOption(0)
    return (+RelativePositionImpl(_x, _y)).apply(builder).apply {
        _x.set(redraw, x)
        _y.set(redraw, y)
    }
}

sealed interface RelativePosition<S> : ViewProtocol<S, RelativePosition<S>> where S : ViewBase

private class RelativePositionImpl constructor(private val x: ViewOption<Int>, private val y: ViewOption<Int>) : SingleViewContainer(), RelativePosition<RelativePositionImpl> {

    override val selfView: RelativePositionImpl = this
    override val selfAPI: RelativePosition<RelativePositionImpl> = this

    override fun size(drawableData: DrawableData): Element {
        return Element(0, 0)
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        child?.size(drawableData, screenSize.copy(), location.copy() + Element(x.get(), y.get()), viewState)
    }

    override fun spacers(orientation: Orientation): Int {
        return 0
    }
}