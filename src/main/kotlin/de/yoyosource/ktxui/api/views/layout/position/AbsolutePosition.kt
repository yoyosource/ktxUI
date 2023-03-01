package de.yoyosource.ktxui.api.views.layout.position

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.*
import kotlin.reflect.KProperty0

fun ViewContainer.AbsolutePosition(x: Int, y: Int, builder: SingleViewBuilder): AbsolutePosition<*> {
    return (+AbsolutePositionImpl(ViewOption(x), ViewOption(y))).apply(builder)
}

fun ViewContainer.AbsolutePosition(x: KProperty0<Int>, y: Int, builder: SingleViewBuilder): AbsolutePosition<*> {
    val _x = ViewOption(0)
    return (+AbsolutePositionImpl(_x, ViewOption(y))).apply(builder).apply {
        _x.set(redraw, x)
    }
}

fun ViewContainer.AbsolutePosition(x: Int, y: KProperty0<Int>, builder: SingleViewBuilder): AbsolutePosition<*> {
    val _y = ViewOption(0)
    return (+AbsolutePositionImpl(ViewOption(x), _y)).apply(builder).apply {
        _y.set(redraw, y)
    }
}

fun ViewContainer.AbsolutePosition(x: KProperty0<Int>, y: KProperty0<Int>, builder: SingleViewBuilder): AbsolutePosition<*> {
    val _x = ViewOption(0)
    val _y = ViewOption(0)
    return (+AbsolutePositionImpl(_x, _y)).apply(builder).apply {
        _x.set(redraw, x)
        _y.set(redraw, y)
    }
}

sealed interface AbsolutePosition<S> : ViewProtocol<S, AbsolutePosition<S>> where S : ViewBase

private class AbsolutePositionImpl constructor(private val x: ViewOption<Int>, private val y: ViewOption<Int>) : SingleViewContainer(), AbsolutePosition<AbsolutePositionImpl> {

    override val selfView: AbsolutePositionImpl = this
    override val selfAPI: AbsolutePosition<AbsolutePositionImpl> = this

    override fun size(drawableData: DrawableData): Element {
        return Element(0, 0)
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        child?.size(drawableData, screenSize.copy(), Element(x.get(), y.get()), viewState)
    }

    override fun spacers(orientation: Orientation): Int {
        return 0
    }
}