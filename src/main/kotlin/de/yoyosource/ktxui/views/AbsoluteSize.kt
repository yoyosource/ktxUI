package de.yoyosource.ktxui.views

import de.yoyosource.ktxui.*
import de.yoyosource.ktxui.utils.Element
import de.yoyosource.ktxui.utils.Orientation
import de.yoyosource.ktxui.utils.ViewState
import kotlin.reflect.KProperty0

fun ViewContainer.AbsoluteSize(width: Int, height: Int, builder: SingleViewContainer.() -> Unit) {
    (+AbsoluteSize(ViewOption(width), ViewOption(height))).builder()
}

fun ViewContainer.AbsoluteSize(width: KProperty0<Int>, height: Int, builder: SingleViewContainer.() -> Unit) {
    val _width = ViewOption(0)
    (+AbsoluteSize(_width, ViewOption(height))).apply(builder).let {
        _width.set(it, width)
    }
}

fun ViewContainer.AbsoluteSize(width: Int, height: KProperty0<Int>, builder: SingleViewContainer.() -> Unit) {
    val _height = ViewOption(0)
    (+AbsoluteSize(ViewOption(width), _height)).apply(builder).let {
        _height.set(it, height)
    }
}

fun ViewContainer.AbsoluteSize(width: KProperty0<Int>, height: KProperty0<Int>, builder: SingleViewContainer.() -> Unit) {
    val _width = ViewOption(0)
    val _height = ViewOption(0)
    (+AbsoluteSize(_width, _height)).apply(builder).let {
        _width.set(it, width)
        _height.set(it, height)
    }
}

private class AbsoluteSize constructor(private val width: ViewOption<Int>, private val height: ViewOption<Int>) : SingleViewContainer() {

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