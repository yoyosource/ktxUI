package de.yoyosource.ktxui.api.views.layout.size

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.*
import kotlin.reflect.KProperty0

fun ViewContainer.AbsoluteSize(width: Int, height: Int, builder: SingleViewBuilder): AbsoluteSize<*> {
    return (+AbsoluteSizeImpl(ViewOption(width), ViewOption(height))).apply(builder)
}

fun ViewContainer.AbsoluteSize(width: KProperty0<Int>, height: Int, builder: SingleViewBuilder): AbsoluteSize<*> {
    val _width = ViewOption(0)
    return (+AbsoluteSizeImpl(_width, ViewOption(height))).apply(builder).apply {
        _width.set(redraw, width)
    }
}

fun ViewContainer.AbsoluteSize(width: Int, height: KProperty0<Int>, builder: SingleViewBuilder): AbsoluteSize<*> {
    val _height = ViewOption(0)
    return (+AbsoluteSizeImpl(ViewOption(width), _height)).apply(builder).apply {
        _height.set(redraw, height)
    }
}

fun ViewContainer.AbsoluteSize(width: KProperty0<Int>, height: KProperty0<Int>, builder: SingleViewBuilder): AbsoluteSize<*> {
    val _width = ViewOption(0)
    val _height = ViewOption(0)
    return (+AbsoluteSizeImpl(_width, _height)).apply(builder).apply {
        _width.set(redraw, width)
        _height.set(redraw, height)
    }
}

sealed interface AbsoluteSize<S> : ViewProtocol<S, AbsoluteSize<S>> where S : ViewBase

private class AbsoluteSizeImpl constructor(private val width: ViewOption<Int>, private val height: ViewOption<Int>) : SingleViewContainer(), AbsoluteSize<AbsoluteSizeImpl> {

    override val selfAPI: AbsoluteSize<AbsoluteSizeImpl> = this
    override val selfView: AbsoluteSizeImpl = this

    override fun size(drawableData: DrawableData): Element {
        return Element(width.get(), height.get())
    }

    override fun size(drawableData: DrawableData, screenSize: Element, location: Element, viewState: ViewState) {
        child?.size(drawableData, Element(width.get(), height.get()), location.copy(), viewState)
        location + Element(width.get(), height.get())
    }

    override fun spacers(orientation: Orientation): Int {
        return 0
    }
}